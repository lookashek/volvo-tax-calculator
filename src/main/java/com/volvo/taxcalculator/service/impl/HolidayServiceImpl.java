package com.volvo.taxcalculator.service.impl;


import com.volvo.taxcalculator.model.HolidayEntity;
import com.volvo.taxcalculator.repository.HolidayRepository;
import com.volvo.taxcalculator.service.HolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HolidayServiceImpl implements HolidayService {

    private final HolidayRepository holidayRepository;

    @Override
    public List<HolidayEntity> findAll() {
        return holidayRepository.findAll();
    }
}
