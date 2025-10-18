package org.example.rental_agreement.model;

import lombok.Getter;

/**
 * Enum for available tools
 */
@Getter
public enum Tool {
    CHNS(ToolType.CHAINSAW, "Chainsaw", "Stihl"),
    LADW(ToolType.LADDER, "Ladder", "Werner"),
    JAKD(ToolType.JACKHAMMER, "Jackhammer", "DeWalt"),
    JAKR(ToolType.JACKHAMMER, "Jackhammer", "Ridgid");

    final String typeName;
    final ToolType toolType;
    final String brand;

    Tool(ToolType toolType, String typeName, String brand) {
        this.toolType = toolType;
        this.typeName = typeName;
        this.brand = brand;
    }
}

