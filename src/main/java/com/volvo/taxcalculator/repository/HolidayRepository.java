package com.volvo.taxcalculator.repository;

import com.volvo.taxcalculator.model.HolidayEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HolidayRepository extends JpaRepository<HolidayEntity, Long> {

}
