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
     * @param rentalRequest The request from the client.
     * @return the rental agreement.
     * @throws IllegalArgumentException on bad inputs in the client request.
     */
    public RentalAgreement generateRentalAgreement(RentalRequest rentalRequest) throws IllegalArgumentException {
        valdidateRentalRequest(rentalRequest);
        Tool tool = Tool.valueOf(rentalRequest.getToolCode());

        // Get the number of chargeable days
        long chargeableDays = ChargingService.getChargeableDays(rentalRequest.getCheckoutDate(),
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

        Date lastDate = ChargingService.getEndDate(rentalRequest.getCheckoutDate(), rentalRequest.getRentalDayCount());
        // We want the due date to be on the last day of the rental, not the day after
        Date dueDate = ChargingService.addDays(lastDate, -1);
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
     * Validates the fields of a RentalRequest.
     * @param rentalRequest The RentalRequest to validate.
     * @throws IllegalArgumentException if any of the fields are invalid.
     */

    public void valdidateRentalRequest(RentalRequest rentalRequest) throws IllegalArgumentException {
        if (rentalRequest.getRentalDayCount() < 1) {
            throw new IllegalArgumentException(("Error: The length of the rental must be at least one day."));
        }
        if (rentalRequest.getCheckoutDate() == null) {
            throw new IllegalArgumentException("Error: You must include the checkout day in the request.");
        }
        if (rentalRequest.getDiscountPercent() < 0 || rentalRequest.getDiscountPercent() > 100) {
            throw new IllegalArgumentException("Error: The discount percentage must be between 0 and 100.");
        }
        if (rentalRequest.getToolCode() == null) {
            throw new IllegalArgumentException("Error: You must include the code for the tool in the request.");
        }
    }
}
