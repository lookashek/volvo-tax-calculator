package com.volvo.taxcalculator.service.impl;

import com.volvo.taxcalculator.configuration.TaxCalculatorConfig;
import com.volvo.taxcalculator.exception.CustomProblemException;
import com.volvo.taxcalculator.model.TollFeeEntity;
import com.volvo.taxcalculator.model.Vehicle;
import com.volvo.taxcalculator.service.HolidayService;
import com.volvo.taxcalculator.service.TaxCalculatorService;
import com.volvo.taxcalculator.service.TollFeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.volvo.taxcalculator.exception.CustomProblemException.Problem.YEAR_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TaxCalculatorServiceImpl implements TaxCalculatorService {

    private final TaxCalculatorConfig config;
    private final TollFeeService tollFeeService;
    private final HolidayService holidayService;


    @Override
    public int calculateTax(Vehicle vehicle, LocalDateTime[] dates) {
        Arrays.sort(dates);
        int totalTax = 0;
        List<LocalDateTime> tempDates = new ArrayList<>();

        for (int i = 0; i < dates.length; i++) {
            tempDates.add(dates[i]);


            if (i == dates.length - 1 || isDifferentDay(dates[i], dates[i + 1])) {
                LocalDateTime[] dayDates = new LocalDateTime[tempDates.size()];
                tempDates.toArray(dayDates);

                totalTax += getTaxForOneDay(vehicle, dayDates);

                tempDates.clear();
            }
        }

        return totalTax;
    }

    private int getTaxForOneDay(Vehicle vehicle, LocalDateTime[] dates) {
        int oneChargeRuleDuration = config.getOneChargeRuleDuration();
        int maxAmount = config.getMaxAmountPerDay();
        int totalFee = 0;
        LocalDateTime intervalStart = null;
        int currentMaxFee = 0;

        for (LocalDateTime date : dates) {
            int nextFee = getTollFee(date, vehicle);

            if (intervalStart == null) {
                intervalStart = date;
                currentMaxFee = nextFee;
            }

            long minutes = Duration.between(intervalStart, date).toMinutes();

            if (minutes <= oneChargeRuleDuration) {
                currentMaxFee = Math.max(currentMaxFee, nextFee);
            } else {
                totalFee += currentMaxFee;
                intervalStart = date;
                currentMaxFee = nextFee;
            }
        }

        totalFee += currentMaxFee;
        return Math.min(totalFee, maxAmount);
    }

    private boolean isTollFreeVehicle(Vehicle vehicle) {
        if (vehicle == null) return false;
        var tollFreeVehicles = config.getTollFreeVehicles();
        return tollFreeVehicles.contains(vehicle);
    }

    private int getTollFee(LocalDateTime date, Vehicle vehicle) {
        if (isTollFreeDate(date) || isTollFreeVehicle(vehicle)) return 0;

        Set<TollFeeEntity> tollFeeEntities = tollFeeService.findAllByYear(date.getYear());
        if (tollFeeEntities.isEmpty())
            throw new CustomProblemException(YEAR_NOT_FOUND, String.format("Provided year not found: %d", date.getYear()));


        return tollFeeEntities.stream()
                .filter(tollFeeDTO -> tollFeeDTO.getFee(date) > 0)
                .mapToInt(TollFeeEntity::getFee)
                .min()
                .orElse(0);
    }

    private boolean isTollFreeDate(LocalDateTime date) {
        DayOfWeek day = date.getDayOfWeek();

        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) return true;

        return holidayService.findAll().stream()
                .filter(holidayEntity -> holidayEntity.isHoliday(date))
                .count() > 0;
    }

    private boolean isDifferentDay(LocalDateTime firstDay, LocalDateTime secondDay) {
        return !firstDay.toLocalDate().equals(secondDay.toLocalDate());
    }

}
