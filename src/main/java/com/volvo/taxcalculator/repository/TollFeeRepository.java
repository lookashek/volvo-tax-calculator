package com.volvo.taxcalculator.repository;

import com.volvo.taxcalculator.model.TollFeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TollFeeRepository extends JpaRepository<TollFeeEntity, Long> {

    Set<TollFeeEntity> findAllByYear(int year);
}
