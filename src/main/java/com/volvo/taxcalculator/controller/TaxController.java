package com.volvo.taxcalculator.controller;

import com.volvo.taxcalculator.model.TaxRequest;
import com.volvo.taxcalculator.service.TaxCalculatorService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/tax")
@RequiredArgsConstructor
public class TaxController {

    final TaxCalculatorService taxCalculatorService;

    @PostMapping("/calculate")
    @Operation(description = "Calculate tax for motorway tolls")
    public String calculateTax(@Valid @RequestBody TaxRequest request) {
        int tax = taxCalculatorService.calculateTax(request.getVehicleType(), request.getDates());
        return String.format("Total tax deducted from your rides is: SEK %d", tax);
    }
}
