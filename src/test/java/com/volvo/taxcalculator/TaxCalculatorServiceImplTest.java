package com.volvo.taxcalculator;

import com.volvo.taxcalculator.configuration.TaxCalculatorConfig;
import com.volvo.taxcalculator.exception.CustomProblemException;
import com.volvo.taxcalculator.model.HolidayEntity;
import com.volvo.taxcalculator.model.TollFeeEntity;
import com.volvo.taxcalculator.model.Vehicle;
import com.volvo.taxcalculator.service.HolidayService;
import com.volvo.taxcalculator.service.TollFeeService;
import com.volvo.taxcalculator.service.impl.TaxCalculatorServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.volvo.taxcalculator.exception.CustomProblemException.Problem.YEAR_NOT_FOUND;
import static com.volvo.taxcalculator.model.Vehicle.CAR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TaxCalculatorServiceImplTest {

    @Mock
    private TaxCalculatorConfig config;

    @Mock
    private TollFeeService tollFeeService;

    @Mock
    private HolidayService holidayService;

    @InjectMocks
    private TaxCalculatorServiceImpl taxCalculatorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculateTaxForSingleDay() {
        //given
        int maxAmount = 60;
        TollFeeEntity tollFeeEntity1 = getTollFeeEntity();
        LocalDateTime[] dates = {
                LocalDateTime.parse("2013-02-08T06:27:00"),
                LocalDateTime.parse("2013-02-08T06:20:00")
        };

        when(config.getOneChargeRuleDuration()).thenReturn(maxAmount);
        when(config.getMaxAmountPerDay()).thenReturn(maxAmount);
        when(tollFeeService.findAllByYear(2013)).thenReturn(Set.of(tollFeeEntity1));

        //when
        int tax = taxCalculatorService.calculateTax(CAR, dates);

        //then
        assertEquals(50, tax);
    }

    @Test
    public void testCalculateTaxForTollFreeDay() {
        //given
        LocalDateTime[] dates = {
                LocalDateTime.parse("2013-01-05T08:00:00")
        };

        HolidayEntity freeDay = getHolidayEntity();

        //when
        when(tollFeeService.findAllByYear(2013)).thenReturn(Set.of());
        when(holidayService.findAll()).thenReturn(List.of(freeDay));
        int tax = taxCalculatorService.calculateTax(CAR, dates);

        //then
        assertEquals(0, tax);
    }

    @Test
    public void testCalculateTaxWithNoTollFeeData() {
        //given
        LocalDateTime[] dates = {LocalDateTime.parse("2013-09-02T08:00:00")};
        when(tollFeeService.findAllByYear(2013)).thenReturn(Collections.emptySet());

        //expect
        CustomProblemException customProblemException = Assertions.assertThrows(CustomProblemException.class, () -> taxCalculatorService.calculateTax(CAR, dates));
        assertEquals("Provided year not found: 2013", customProblemException.getMessage());
    }


    private static TollFeeEntity getTollFeeEntity() {
        TollFeeEntity tollFeeEntity = new TollFeeEntity();
        tollFeeEntity.setStartHour(6);
        tollFeeEntity.setStartMinute(0);
        tollFeeEntity.setEndHour(9);
        tollFeeEntity.setEndMinute(0);
        tollFeeEntity.setYear(2013);
        tollFeeEntity.setFee(50);
        return tollFeeEntity;
    }

    private static HolidayEntity getHolidayEntity() {
        HolidayEntity holidayEntity = new HolidayEntity();
        holidayEntity.setMonth(1);
        holidayEntity.setDay(5);
        return holidayEntity;
    }
}
