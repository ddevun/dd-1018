package org.example.rental_agreement.model;

import lombok.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DAO for the rental agreement to be returned to the customer.
 */

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

    public RentalAgreement(String toolCode, String toolType, String toolBrand, long rentalDays, Date checkoutDate, Date dueDate, BigDecimal dailyRentalCharge, long chargeDays, BigDecimal preDiscountCharge, BigDecimal discountAmount, BigDecimal finalCharge, int discountPercent) {
        this.toolCode = toolCode;
        this.toolType = toolType;
        this.toolBrand = toolBrand;
        this.rentalDays = rentalDays;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
        this.dailyRentalCharge = dailyRentalCharge;
        this.chargeDays = chargeDays;
        this.preDiscountCharge = preDiscountCharge;
        this.discountAmount = discountAmount;
        this.finalCharge = finalCharge;
        this.discountPercent = discountPercent;
    }

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
    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    public String getToolBrand() {
        return toolBrand;
    }

    public void setToolBrand(String toolBrand) {
        this.toolBrand = toolBrand;
    }

    public long getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(long rentalDays) {
        this.rentalDays = rentalDays;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getDailyRentalCharge() {
        return dailyRentalCharge;
    }

    public void setDailyRentalCharge(BigDecimal dailyRentalCharge) {
        this.dailyRentalCharge = dailyRentalCharge;
    }

    public long getChargeDays() {
        return chargeDays;
    }

    public void setChargeDays(long chargeDays) {
        this.chargeDays = chargeDays;
    }

    public BigDecimal getPreDiscountCharge() {
        return preDiscountCharge;
    }

    public void setPreDiscountCharge(BigDecimal preDiscountCharge) {
        this.preDiscountCharge = preDiscountCharge;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getFinalCharge() {
        return finalCharge;
    }

    public void setFinalCharge(BigDecimal finalCharge) {
        this.finalCharge = finalCharge;
    }
}
