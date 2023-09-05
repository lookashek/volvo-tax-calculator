package com.volvo.taxcalculator.service;

import com.volvo.taxcalculator.model.Vehicle;

import java.time.LocalDateTime;

public interface TaxCalculatorService {

    int calculateTax(Vehicle vehicle, LocalDateTime[] dates);
}
