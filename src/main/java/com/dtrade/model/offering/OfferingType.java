package com.dtrade.model.offering;

import java.time.Duration;

/**
 * Created by kudelin on 1/4/17.
 */
public enum OfferingType {

    ONE_HOUR(Duration.ofHours(1).toMillis()),
    THREE_HOURS(Duration.ofHours(3).toMillis()),
    SIX_HOURS(Duration.ofHours(6).toMillis()),
    ONE_DAY(Duration.ofDays(1).toMillis()),
    TWO_DAYS(Duration.ofDays(2).toMillis()),
    ONE_WEEK(Duration.ofDays(7).toMillis()),
    TWO_WEEKS(Duration.ofDays(14).toMillis());

    private Long duration;

    OfferingType(Long duration){
        this.duration = duration;
    }

    public Long getDuration(){
        return this.duration;
    }
}
