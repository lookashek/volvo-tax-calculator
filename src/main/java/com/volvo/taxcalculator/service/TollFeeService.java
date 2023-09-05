package com.volvo.taxcalculator.service;

import com.volvo.taxcalculator.model.TollFeeEntity;

import java.util.Set;

public interface TollFeeService {

    Set<TollFeeEntity> findAllByYear(int year);
}
