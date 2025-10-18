package org.example.rental_agreement.service;

import org.example.rental_agreement.dto.CheckoutRequest;
import org.example.rental_agreement.model.RentalAgreement;
import org.example.rental_agreement.model.Tool;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.Date;

@Service
public class RentalService {
    public RentalAgreement generateRentalAgreement(CheckoutRequest checkoutRequest) throws IllegalArgumentException {

        if (checkoutRequest.getRentalDayCount() < 1) {
            throw new IllegalArgumentException("Please select a rental day length of one day or greater.");
        }
        if (checkoutRequest.getDiscountPercent() < 0 || checkoutRequest.getDiscountPercent() > 100) {
            throw new IllegalArgumentException("Please select a discount percentage between 0 and 100.");
        }

        Tool tool = Tool.valueOf(checkoutRequest.getToolCode());

        long chargeableDays = DateService.getChargeableDays(checkoutRequest.getCheckoutDate(),
                checkoutRequest.getRentalDayCount(),
                tool.getToolType().isWeekdayCharge(),
                tool.getToolType().isWeekendCharge(),
                tool.getToolType().isHolidayCharge());
        double preDiscountCharge = chargeableDays * tool.getToolType().getDailyCharge().doubleValue();
        BigDecimal preDiscountChargeRounded = new BigDecimal(preDiscountCharge)
                .setScale(2, RoundingMode.HALF_UP);
        double discountAmount = preDiscountCharge * ((double)checkoutRequest.getDiscountPercent() / 100);
        BigDecimal discountAmountRounded = new BigDecimal(discountAmount)
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal finalCharge = new BigDecimal(preDiscountChargeRounded.doubleValue() - discountAmountRounded.doubleValue())
                .setScale(2, RoundingMode.HALF_UP);

        Date lastDate = DateService.getFinalDate(checkoutRequest.getCheckoutDate(), checkoutRequest.getRentalDayCount());
        // We want the due date to be on the last day of the rental, not the day after
        Date dueDate = DateService.addDays(lastDate, -1);
        RentalAgreement rentalAgreement = new RentalAgreement(
                tool.toString(),
                tool.getToolType().toString(),
                tool.getBrand(),
                Duration.between(checkoutRequest.getCheckoutDate().toInstant(), lastDate.toInstant()).toDays(),
                checkoutRequest.getCheckoutDate(),
                dueDate,
                tool.getToolType().getDailyCharge(),
                chargeableDays,
                preDiscountChargeRounded,
                discountAmountRounded,
                finalCharge,
                checkoutRequest.getDiscountPercent());
        return rentalAgreement;
    }

    public static void outputRentalAgreement(RentalAgreement rentalAgreement) {
        System.out.println(rentalAgreement);
    }

}
