package org.example.rental_agreement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Enum for available tools
 */
public enum Tool {
    CHNS(ToolType.CHAINSAW, "Stihl"),
    LADW(ToolType.LADDER, "Werner"),
    JAKD(ToolType.JACKHAMMER, "DeWalt"),
    JAKR(ToolType.JACKHAMMER, "Ridgid");

    final ToolType toolType;
    final String brand;

    public ToolType getToolType() {
        return toolType;
    }

    public String getBrand() {
        return brand;
    }

    Tool(ToolType toolType, String brand) {
        this.toolType = toolType;
        this.brand = brand;
    }
}

