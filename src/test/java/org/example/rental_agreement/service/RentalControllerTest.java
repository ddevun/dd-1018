package org.example.rental_agreement.service;

import org.example.rental_agreement.controller.RentalController;
import org.example.rental_agreement.dto.RentalAgreement;
import org.example.rental_agreement.dto.RentalRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import java.text.ParseException;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class RentalControllerTest {
    @InjectMocks
    RentalController rentalController;

    @Mock
    RentalService rentalService;

    @Mock
    RentalRequest rentalRequest;

    @Mock
    RentalAgreement rentalAgreement;

    @Test
    public void testRentalController_happyPath() throws ParseException {
        Mockito.when(rentalService.generateRentalAgreement(rentalRequest)).thenReturn(rentalAgreement);
        rentalController.rental(rentalRequest);
        Mockito.verify(rentalService, times(1)).generateRentalAgreement(rentalRequest);
    }

    @Test
    public void testRentalController_badValidation() {
        Assertions.assertThrows(ResponseStatusException.class, () -> rentalController.rental(rentalRequest));
    }
}
