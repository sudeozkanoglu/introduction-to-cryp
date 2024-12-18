package org.app.g201210034.model.entity.reservation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.app.g201210034.model.entity.car.Car;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @Column(name = "reservation_date")
    private LocalDateTime reservationStartDate;

    @Column(name = "reservation_end_date")
    private LocalDateTime reservationEndDate;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "start_location")
    private String startLocation;

    @Column(name = "end_location")
    private String endLocation;

    @CreationTimestamp
    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "number_of_people")
    private Long numberOfPeople;

    @Column(name = "luggage_count")
    private Long luggageCount;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @Column(name = "identity_number")
    private String identityNumber;

    @Column(name = "hour")
    private Long hour;

    @Column(name = "isConfirmed")
    private Boolean isConfirmed;

    //Car need to be added
    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;


}
