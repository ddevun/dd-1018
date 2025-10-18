package org.example.rental_agreement.service;

import org.example.rental_agreement.dto.RentalRequest;
import org.example.rental_agreement.dto.RentalAgreement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.stream.Stream;

/**
 * Tests for Rental Service
 */
public class RentalServiceTest {

   static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    RentalService rentalService = new RentalService();

    /**
     * Test data with bad rental request inputs.
     * @return
     * @throws ParseException
     */
    static Stream<Arguments> badTestData() throws ParseException {
       return Stream.of(Arguments.of("JAKR", "2015-09-03", 5, 101),
                        Arguments.of("JAKR", "2015-09-03", 0, 100));
    }

    /**
     * Test data for the happy path.
      * @return a stream of arguments
     * @throws ParseException
     */
   static Stream<Arguments> testData() throws ParseException {
       RentalAgreement[] rentalAgreements = {
               RentalAgreement.builder()
                       .toolCode("LADW")
                       .toolType("Ladder")
                       .toolBrand("Werner")
                       .rentalDays(3)
                       .checkoutDate(simpleDateFormat.parse("2020-07-02"))
                       .dueDate(simpleDateFormat.parse("2020-07-04"))
                       .dailyRentalCharge(new BigDecimal("1.99"))
                       .chargeDays(2)
                       .preDiscountCharge(new BigDecimal("3.98"))
                       .discountPercent(10)
                       .discountAmount(new BigDecimal("0.40"))
                       .finalCharge(new BigDecimal("3.58"))
                       .build(),
               RentalAgreement.builder()
                       .toolCode("CHNS")
                       .toolType("Chainsaw")
                       .toolBrand("Stihl")
                       .rentalDays(5)
                       .checkoutDate(simpleDateFormat.parse("2015-07-02"))
                       .dueDate(simpleDateFormat.parse("2015-07-06"))
                       .dailyRentalCharge(new BigDecimal("1.49"))
                       .chargeDays(3)
                       .preDiscountCharge(new BigDecimal("4.47"))
                       .discountPercent(25)
                       .discountAmount(new BigDecimal("1.12"))
                       .finalCharge(new BigDecimal("3.35"))
                       .build(),
               RentalAgreement.builder()
                       .toolCode("JAKD")
                       .toolType("Jackhammer")
                       .toolBrand("DeWalt")
                       .rentalDays(6)
                       .checkoutDate(simpleDateFormat.parse("2015-09-03"))
                       .dueDate(simpleDateFormat.parse("2015-09-08"))
                       .dailyRentalCharge(new BigDecimal("2.99"))
                       .chargeDays(3)
                       .preDiscountCharge(new BigDecimal("8.97"))
                       .discountPercent(0)
                       .discountAmount(new BigDecimal("0.00"))
                       .finalCharge(new BigDecimal("8.97"))
                       .build(),
               RentalAgreement.builder()
                       .toolCode("JAKR")
                       .toolType("Jackhammer")
                       .toolBrand("Ridgid")
                       .rentalDays(9)
                       .checkoutDate(simpleDateFormat.parse("2015-07-02"))
                       .dueDate(simpleDateFormat.parse("2015-07-10"))
                       .dailyRentalCharge(new BigDecimal("2.99"))
                       .chargeDays(6)
                       .preDiscountCharge(new BigDecimal("17.94"))
                       .discountPercent(0)
                       .discountAmount(new BigDecimal("0.00"))
                       .finalCharge(new BigDecimal("17.94"))
                       .build(),
               RentalAgreement.builder()
                       .toolCode("JAKR")
                       .toolType("Jackhammer")
                       .toolBrand("Ridgid")
                       .rentalDays(4)
                       .checkoutDate(simpleDateFormat.parse("2020-07-02"))
                       .dueDate(simpleDateFormat.parse("2020-07-05"))
                       .dailyRentalCharge(new BigDecimal("2.99"))
                       .chargeDays(1)
                       .preDiscountCharge(new BigDecimal("2.99"))
                       .discountPercent(50)
                       .discountAmount(new BigDecimal("1.50"))
                       .finalCharge(new BigDecimal("1.49"))
                       .build()};

        return Stream.of(
                        Arguments.of("LADW", "2020-07-02", 3, 10, rentalAgreements[0]),
                        Arguments.of("CHNS", "2015-07-02", 5, 25, rentalAgreements[1]),
                        Arguments.of("JAKD", "2015-09-03", 6, 0, rentalAgreements[2]),
                        Arguments.of("JAKR", "2015-07-02", 9, 0, rentalAgreements[3]),
                        Arguments.of("JAKR", "2020-07-02", 4, 50, rentalAgreements[4]));
    };

    /**
     * Tests a rental request with bad inputs
     * @param toolCode
     * @param date
     * @param rentalDayCount
     * @param discountPercent
     */
   @ParameterizedTest
   @MethodSource("badTestData")
   public void testRentalAgreement_badInput(String toolCode, String date, int rentalDayCount, int discountPercent) throws ParseException {
       RentalRequest rentalRequest = RentalRequest.builder()
               .toolCode(toolCode)
               .checkoutDate(simpleDateFormat.parse(date))
               .rentalDayCount(rentalDayCount)
               .discountPercent(discountPercent)
               .build();

       Assertions.assertThrows(IllegalArgumentException.class, () -> rentalService.generateRentalAgreement(rentalRequest));
   }

    /**
     * Happy path test
     * @param toolCode
     * @param date
     * @param rentalDayCount
     * @param discountPercent
     * @param rentalAgreement
     * @throws ParseException
     */
    @ParameterizedTest
    @MethodSource("testData")
    public void testRentalAgreement(String toolCode, String date, int rentalDayCount, int discountPercent, RentalAgreement rentalAgreement) throws ParseException {

        RentalRequest rentalRequest = RentalRequest.builder()
                .toolCode(toolCode)
                .checkoutDate(simpleDateFormat.parse(date))
                .rentalDayCount(rentalDayCount)
                .discountPercent(discountPercent)
                .build();

        RentalAgreement response = rentalService.generateRentalAgreement(rentalRequest);
        RentalService.outputRentalAgreement(response);
        Assertions.assertEquals(rentalAgreement, response);

    }

}
