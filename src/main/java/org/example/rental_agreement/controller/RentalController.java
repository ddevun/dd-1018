package org.example.rental_agreement.controller;

import org.example.rental_agreement.dto.RentalRequest;
import org.example.rental_agreement.service.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Controller to receive a rental request, and return a formatted rental agreement.
 */
@RestController
public class RentalController {
    RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    /**
     * Receives a rental request, and returns a formatted rental agreement.
     * @param rentalRequest The rental request to process.
     * @return a rental agreement.
     */
    @PostMapping("/rental")
    public String rental(@RequestBody RentalRequest rentalRequest) {
        try {
            return rentalService.generateRentalAgreement(rentalRequest).toString();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "We apologize, but there was an error. Please try your request again.");
        }
    }
}
