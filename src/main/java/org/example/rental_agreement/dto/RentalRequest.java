package org.example.rental_agreement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.Date;

/**
 * DTO for the checkout Request from the client.
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
    @JsonFormat(pattern="MM/dd/yy")
    Date checkoutDate;

}
