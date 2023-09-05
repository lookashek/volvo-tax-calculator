package com.volvo.taxcalculator.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "toll_fee")
public class TollFeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_hour")
    private Integer startHour;

    @Column(name = "start_minute")
    private Integer startMinute;

    @Column(name = "end_hour")
    private Integer endHour;

    @Column(name = "end_minute")
    private Integer endMinute;

    @Column(name = "year_number")
    private Integer year;

    @Column(name = "fee")
    private Integer fee;

    public Integer getFee(LocalDateTime date) {
        int hour = date.getHour();
        int minute = date.getMinute();
        if ((hour > startHour || (hour == startHour && minute >= startMinute)) &&
                (hour < endHour || (hour == endHour && minute <= endMinute))) {
            return fee;
        } else {
            return 0;
        }
    }
}
