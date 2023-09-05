package com.volvo.taxcalculator.configuration;

import com.volvo.taxcalculator.model.Vehicle;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Getter
public class TaxCalculatorConfig {

    @Value("${app.tollFreeVehicles}")
    private Set<Vehicle> tollFreeVehicles;
    @Value("${app.year}")
    private int year;

    @Value("${app.maxAmountPerDay}")
    private int maxAmountPerDay;

    @Value("${app.maxOneChargeDuration}")
    private int oneChargeRuleDuration;


}
