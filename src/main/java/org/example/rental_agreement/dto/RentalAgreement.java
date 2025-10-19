package org.example.rental_agreement.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * DTO for the rental agreement to be returned to the customer.
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
    @JsonFormat(pattern="MM/dd/yy")
    Date checkoutDate;
    @JsonFormat(pattern="MM/dd/yy")
    Date dueDate;
    BigDecimal dailyRentalCharge;
    long chargeDays;
    BigDecimal preDiscountCharge;
    BigDecimal discountAmount;
    BigDecimal finalCharge;
    int discountPercent;

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
        StringBuilder builder = new StringBuilder("\n");
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

    public void print() {
        System.out.println(this);
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
                  && Objects.equals(this.toolType, rentalAgreement.getToolType())
                  && Objects.equals(this.toolBrand, rentalAgreement.getToolBrand())
                  && this.rentalDays == rentalAgreement.getRentalDays()
                  && Objects.equals(this.checkoutDate, rentalAgreement.getCheckoutDate())
                  && Objects.equals(this.dueDate, rentalAgreement.getDueDate())
                  && Objects.equals(this.dailyRentalCharge, rentalAgreement.getDailyRentalCharge())
                  && this.chargeDays == rentalAgreement.getChargeDays()
                  && Objects.equals(this.preDiscountCharge, rentalAgreement.getPreDiscountCharge())
                  && this.discountPercent == rentalAgreement.discountPercent
                  && Objects.equals(this.discountAmount, rentalAgreement.getDiscountAmount())
                  && Objects.equals(this.finalCharge, rentalAgreement.getFinalCharge());
    }

    @Override
    public int hashCode() {
        return (int)
                (31 *
                (toolCode == null? 0: toolCode.hashCode())
                + (toolType == null? 0: toolType.hashCode())
                + (toolBrand == null? 0: toolBrand.hashCode())
                + rentalDays
                + (checkoutDate == null? 0:checkoutDate.hashCode())
                + (dueDate == null? 0:dueDate.hashCode())
                + (dailyRentalCharge == null? 0: dailyRentalCharge.hashCode())
                + chargeDays
                + (preDiscountCharge == null? 0: preDiscountCharge.hashCode())
                + discountPercent
                + (discountAmount == null? 0: discountAmount.hashCode())
                + (finalCharge == null? 0: finalCharge.hashCode()));

    }

}
