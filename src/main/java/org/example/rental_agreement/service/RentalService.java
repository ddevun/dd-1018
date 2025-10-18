package org.example.rental_agreement.service;

import org.example.rental_agreement.dto.RentalRequest;
import org.example.rental_agreement.dto.RentalAgreement;
import org.example.rental_agreement.model.Tool;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.Date;

/**
 * Service to generate a rental agreement.
 */
@Service
public class RentalService {
    /**
     * Generates a rental agreement.
     * @param rentalRequest The request from the client
     * @return the rental agreement
     * @throws IllegalArgumentException on bad inputs in the client request.
     */
    public RentalAgreement generateRentalAgreement(RentalRequest rentalRequest) throws IllegalArgumentException {

        if (rentalRequest.getRentalDayCount() < 1) {
            throw new IllegalArgumentException("Please select a rental day length of one day or greater.");
        }
        if (rentalRequest.getDiscountPercent() < 0 || rentalRequest.getDiscountPercent() > 100) {
            throw new IllegalArgumentException("Please select a discount percentage between 0 and 100.");
        }

        Tool tool = Tool.valueOf(rentalRequest.getToolCode());

        // Get the number of chargeable days
        long chargeableDays = DateService.getChargeableDays(rentalRequest.getCheckoutDate(),
                rentalRequest.getRentalDayCount(),
                tool.getToolType().isWeekdayCharge(),
                tool.getToolType().isWeekendCharge(),
                tool.getToolType().isHolidayCharge());

        double preDiscountCharge = chargeableDays * tool.getToolType().getDailyCharge().doubleValue();
        BigDecimal preDiscountChargeRounded = new BigDecimal(preDiscountCharge)
                .setScale(2, RoundingMode.HALF_UP);
        double discountAmount = preDiscountCharge * ((double) rentalRequest.getDiscountPercent() / 100);
        BigDecimal discountAmountRounded = new BigDecimal(discountAmount)
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal finalCharge = new BigDecimal(preDiscountChargeRounded.doubleValue() - discountAmountRounded.doubleValue())
                .setScale(2, RoundingMode.HALF_UP);

        Date lastDate = DateService.getEndDate(rentalRequest.getCheckoutDate(), rentalRequest.getRentalDayCount());
        // We want the due date to be on the last day of the rental, not the day after
        Date dueDate = DateService.addDays(lastDate, -1);
        RentalAgreement rentalAgreement = RentalAgreement.builder()
                .toolCode(tool.toString())
                .toolType(tool.getTypeName())
                .toolBrand(tool.getBrand())
                .rentalDays(Duration.between(rentalRequest.getCheckoutDate().toInstant(), lastDate.toInstant()).toDays())
                .checkoutDate(rentalRequest.getCheckoutDate())
                .dueDate(dueDate)
                .dailyRentalCharge(tool.getToolType().getDailyCharge())
                .chargeDays(chargeableDays)
                .preDiscountCharge(preDiscountChargeRounded)
                .discountAmount(discountAmountRounded)
                .finalCharge(finalCharge)
                .discountPercent(rentalRequest.getDiscountPercent())
                .build();
        return rentalAgreement;
    }

    /**
     * prints out the rental agreement to the console.
     * @param rentalAgreement The rental agreement.
     */
    public static void outputRentalAgreement(RentalAgreement rentalAgreement) {
        System.out.println(rentalAgreement);
    }

}
