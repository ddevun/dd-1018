package org.example.rental_agreement.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Enum for tool types.
 */
@Getter

public enum ToolType {
    LADDER("Ladder", new BigDecimal("1.99"), true, true, false),
    CHAINSAW("Chainsaw", new BigDecimal("1.49"), true, false, true),
    JACKHAMMER("Jackhammer", new BigDecimal("2.99"), true, false, false);
    final String name;
    final BigDecimal dailyCharge;
    final boolean weekdayCharge;
    final boolean weekendCharge;
    final boolean holidayCharge;

    ToolType(String name, BigDecimal dailyCharge, boolean weekdayCharge, boolean weekendCharge, boolean holidayCharge) {
        this.name = name;
        this.dailyCharge = dailyCharge;
        this.weekdayCharge = weekdayCharge;
        this.weekendCharge = weekendCharge;
        this.holidayCharge = holidayCharge;
    }
}
