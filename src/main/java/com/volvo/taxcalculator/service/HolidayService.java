package com.volvo.taxcalculator.service;

import com.volvo.taxcalculator.model.HolidayEntity;

import java.util.List;

public interface HolidayService {

    List<HolidayEntity> findAll();
}
