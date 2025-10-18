package org.example.rental_agreement.service;

import org.example.rental_agreement.dto.CheckoutRequest;
import org.example.rental_agreement.model.RentalAgreement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

public class RentalServiceTest {

    @ParameterizedTest
    @CsvSource(
    {
        //"JAKR,2015-09-03,5,101",
        "LADW,2020-07-02,3,10",
        "CHNS,2015-07-02,5,25",
        "JAKD,2015-09-03,6,0",
        "JAKR,2015-07-02,9,0",
        "JAKR,2020-07-02,4,50"
    })
    public void testRentalAgreement(ArgumentsAccessor argumentsAccessor) throws ParseException {
        Assertions.assertNotNull(argumentsAccessor);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        CheckoutRequest checkoutRequest = new CheckoutRequest();
        checkoutRequest.setToolCode((String)argumentsAccessor.get(0));
        checkoutRequest.setCheckoutDate(formatter.parse((String)argumentsAccessor.get(1)));
        String rentalDays = (String)argumentsAccessor.get(2);
        String discountPercent = (String) argumentsAccessor.get(3);
        checkoutRequest.setRentalDayCount(Integer.parseInt(rentalDays));
        checkoutRequest.setDiscountPercent(Integer.parseInt(discountPercent));
        RentalService rentalService = new RentalService();
        RentalAgreement rentalAgreement = rentalService.generateRentalAgreement(checkoutRequest);
        RentalService.outputRentalAgreement(rentalAgreement);

    }

}
