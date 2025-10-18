package org.example.rental_agreement.model;

import lombok.Getter;

import java.math.BigDecimal;

/**
 * Enum for tool types.
 */
public enum ToolType {
    LADDER("Ladder", new BigDecimal("1.99"), true, true, false),
    CHAINSAW("Chainsaw", new BigDecimal("1.49"), true, false, true),
    JACKHAMMER("Jackhammer", new BigDecimal("2.99"), true, false, false);
    final String name;
    final BigDecimal dailyCharge;
    final boolean weekdayCharge;
    final boolean weekendCharge;
    final boolean holidayCharge;

    public String getName() {
        return name;
    }

    public BigDecimal getDailyCharge() {
        return dailyCharge;
    }

    public boolean isWeekdayCharge() {
        return weekdayCharge;
    }

    public boolean isWeekendCharge() {
        return weekendCharge;
    }

    public boolean isHolidayCharge() {
        return holidayCharge;
    }

    ToolType(String name, BigDecimal dailyCharge, boolean weekdayCharge, boolean weekendCharge, boolean holidayCharge) {
        this.name = name;
        this.dailyCharge = dailyCharge;
        this.weekdayCharge = weekdayCharge;
        this.weekendCharge = weekendCharge;
        this.holidayCharge = holidayCharge;
    }
}
