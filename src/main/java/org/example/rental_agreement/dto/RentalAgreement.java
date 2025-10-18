package org.example.rental_agreement.dto;


import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DAO for the rental agreement to be returned to the customer.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentalAgreement {
    String toolCode;
    String toolType;
    String toolBrand;
    long rentalDays;
    Date checkoutDate;
    Date dueDate;
    BigDecimal dailyRentalCharge;
    long chargeDays;
    BigDecimal preDiscountCharge;
    BigDecimal discountAmount;
    BigDecimal finalCharge;
    int discountPercent;

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Tool code: %s\n", toolCode));
        builder.append(String.format("Tool type: %s\n", toolType));
        builder.append(String.format("Tool brand: %s\n", toolBrand));
        builder.append(String.format("Rental days: %d\n", rentalDays));
        builder.append(String.format("Checkout date: %s\n", formatter.format(checkoutDate)));
        builder.append(String.format("Due date: %s\n", formatter.format(dueDate)));
        builder.append(String.format("Daily rental charge: $%s\n", dailyRentalCharge.toString()));
        builder.append(String.format("Charge days: %s\n", chargeDays));
        builder.append(String.format("Pre-discount charge: $%s\n", preDiscountCharge));
        builder.append(String.format("Discount percent: %s%%\n", discountPercent));
        builder.append(String.format("Discount amount: $%s\n", discountAmount.toString()));
        builder.append(String.format("Final charge: $%s\n", finalCharge.toString()));
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RentalAgreement rentalAgreement)) {
            return false;
        }
        return this.toolCode.equals(rentalAgreement.getToolCode())
                  && this.toolType.equals(rentalAgreement.getToolType())
                  && this.toolBrand.equals(rentalAgreement.getToolBrand())
                  && this.rentalDays == rentalAgreement.getRentalDays()
                  && this.checkoutDate.equals(rentalAgreement.getCheckoutDate())
                  && this.dueDate.equals(rentalAgreement.getDueDate())
                  && this.dailyRentalCharge.equals(rentalAgreement.getDailyRentalCharge())
                  && this.chargeDays == rentalAgreement.getChargeDays()
                  && this.preDiscountCharge.equals(rentalAgreement.getPreDiscountCharge())
                  && this.discountPercent == rentalAgreement.discountPercent
                  && this.discountAmount.equals(rentalAgreement.getDiscountAmount())
                  && this.finalCharge.equals(rentalAgreement.getFinalCharge());
    }

    @Override
    public int hashCode() {
        return (int)
                (31 *
                (toolCode == null? 0: toolCode.hashCode())
                + (toolType == null? 0: toolType.hashCode())
                + (toolBrand == null? 0: toolBrand.hashCode())
                + rentalDays
                + checkoutDate.hashCode()
                + dueDate.hashCode()
                + dailyRentalCharge.hashCode()
                + chargeDays
                + preDiscountCharge.hashCode()
                + discountPercent
                + discountAmount.hashCode()
                + finalCharge.hashCode());

    }

}
