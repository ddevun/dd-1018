package org.example.rental_agreement.service;

import org.example.rental_agreement.dto.CheckoutRequest;
import org.example.rental_agreement.dto.RentalAgreement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.stream.Stream;

public class RentalServiceTest {

    static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
   static Stream<Arguments> testData() throws ParseException {
       RentalAgreement[] rentalAgreements = {
               RentalAgreement.builder()
                       .toolCode("LADW")
                       .toolType("LADDER")
                       .toolBrand("Werner")
                       .rentalDays(3)
                       .checkoutDate(formatter.parse("2020-07-02"))
                       .dueDate(formatter.parse("2020-07-04"))
                       .dailyRentalCharge(new BigDecimal("1.99"))
                       .chargeDays(2)
                       .preDiscountCharge(new BigDecimal("3.98"))
                       .discountPercent(10)
                       .discountAmount(new BigDecimal("0.40"))
                       .finalCharge(new BigDecimal("3.58"))
                       .build(),
               RentalAgreement.builder()
                       .toolCode("CHNS")
                       .toolType("CHAINSAW")
                       .toolBrand("Stihl")
                       .rentalDays(5)
                       .checkoutDate(formatter.parse("2015-07-02"))
                       .dueDate(formatter.parse("2015-07-06"))
                       .dailyRentalCharge(new BigDecimal("1.49"))
                       .chargeDays(3)
                       .preDiscountCharge(new BigDecimal("4.47"))
                       .discountPercent(25)
                       .discountAmount(new BigDecimal("1.12"))
                       .finalCharge(new BigDecimal("3.35"))
                       .build(),
               RentalAgreement.builder()
                       .toolCode("JAKD")
                       .toolType("JACKHAMMER")
                       .toolBrand("DeWalt")
                       .rentalDays(6)
                       .checkoutDate(formatter.parse("2015-09-03"))
                       .dueDate(formatter.parse("2015-09-08"))
                       .dailyRentalCharge(new BigDecimal("2.99"))
                       .chargeDays(3)
                       .preDiscountCharge(new BigDecimal("8.97"))
                       .discountPercent(0)
                       .discountAmount(new BigDecimal("0.00"))
                       .finalCharge(new BigDecimal("8.97"))
                       .build(),
               RentalAgreement.builder()
                       .toolCode("JAKR")
                       .toolType("JACKHAMMER")
                       .toolBrand("Ridgid")
                       .rentalDays(9)
                       .checkoutDate(formatter.parse("2015-07-02"))
                       .dueDate(formatter.parse("2015-07-10"))
                       .dailyRentalCharge(new BigDecimal("2.99"))
                       .chargeDays(6)
                       .preDiscountCharge(new BigDecimal("17.94"))
                       .discountPercent(0)
                       .discountAmount(new BigDecimal("0.00"))
                       .finalCharge(new BigDecimal("17.94"))
                       .build(),
               RentalAgreement.builder()
                       .toolCode("JAKR")
                       .toolType("JACKHAMMER")
                       .toolBrand("Ridgid")
                       .rentalDays(4)
                       .checkoutDate(formatter.parse("2020-07-02"))
                       .dueDate(formatter.parse("2020-07-05"))
                       .dailyRentalCharge(new BigDecimal("2.99"))
                       .chargeDays(1)
                       .preDiscountCharge(new BigDecimal("2.99"))
                       .discountPercent(50)
                       .discountAmount(new BigDecimal("1.50"))
                       .finalCharge(new BigDecimal("1.49"))
                       .build()};

        return Stream.of(
                //Arguments.of("JAKR", "2015-09-03", 5, 101),
                        Arguments.of("LADW", "2020-07-02", 3, 10, rentalAgreements[0]),
                        Arguments.of("CHNS", "2015-07-02", 5, 25, rentalAgreements[1]),
                        Arguments.of("JAKD", "2015-09-03", 6, 0, rentalAgreements[2]),
                        Arguments.of("JAKR", "2015-07-02", 9, 0, rentalAgreements[3]),
                        Arguments.of("JAKR", "2020-07-02", 4, 50, rentalAgreements[4]));
    };
    @ParameterizedTest
    @MethodSource("testData")
    public void testRentalAgreement(String toolCode, String date, int rentalDayCount, int discountPercent, RentalAgreement rentalAgreement) throws ParseException {


        CheckoutRequest checkoutRequest = CheckoutRequest.builder()
                .toolCode(toolCode)
                .checkoutDate(formatter.parse(date))
                .rentalDayCount(rentalDayCount)
                .discountPercent(discountPercent)
                .build();
        RentalService rentalService = new RentalService();
        RentalAgreement response = rentalService.generateRentalAgreement(checkoutRequest);
        RentalService.outputRentalAgreement(response);
        Assertions.assertEquals(rentalAgreement, response);

    }

}
