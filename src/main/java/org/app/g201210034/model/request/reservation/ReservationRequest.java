package org.app.g201210034.model.request.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequest {

    private LocalDateTime reservationStartDate;

    private LocalDateTime reservationEndDate;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String startLocation;

    private String endLocation;

    private String identityNumber;

    private Long numberOfPeople;

    private Long luggageCount;

    private Long hour;

    private Boolean isConfirmed;

    private Long carId;


}
