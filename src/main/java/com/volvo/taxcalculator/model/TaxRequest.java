package com.volvo.taxcalculator.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema
public class TaxRequest {

    @Schema(description = "Type of the vehicle", example = "Car")
    @JsonProperty("vehicle_type")
    private Vehicle vehicleType;

    @Schema(description = "Array of dates for tax calculation", example = "[\"2021-08-01 12:00:00\", \"2021-08-02 12:00:00\"]")
    @JsonProperty(value = "dates", required = true)

    @NotEmpty(message = "dates cannot be empty")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime[] dates;
}
