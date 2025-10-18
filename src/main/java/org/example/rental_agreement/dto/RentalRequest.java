package org.example.rental_agreement.dto;

import lombok.*;

import java.util.Date;

/**
 * DAO for the checkout Request from the client.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RentalRequest {
    String toolCode;
    int rentalDayCount;
    int discountPercent;
    Date checkoutDate;

}
