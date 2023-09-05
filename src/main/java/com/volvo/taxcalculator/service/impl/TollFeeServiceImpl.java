package com.volvo.taxcalculator.service.impl;

import com.volvo.taxcalculator.model.TollFeeEntity;
import com.volvo.taxcalculator.repository.TollFeeRepository;
import com.volvo.taxcalculator.service.TollFeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class TollFeeServiceImpl implements TollFeeService {

    private final TollFeeRepository tollFeeRepository;

    @Override
    public Set<TollFeeEntity> findAllByYear(int year) {
        return tollFeeRepository.findAllByYear(year);
    }
}
