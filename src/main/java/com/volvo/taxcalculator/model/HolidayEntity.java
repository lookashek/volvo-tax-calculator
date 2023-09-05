package com.volvo.taxcalculator.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "holiday")
public class HolidayEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "month_number")
    private Integer month;

    @Column(name = "day_number")
    private Integer day;

    public boolean isHoliday(LocalDateTime date) {
        return date.getMonth().getValue() == month && (day == 0 || date.getDayOfMonth() == day);
    }

}
