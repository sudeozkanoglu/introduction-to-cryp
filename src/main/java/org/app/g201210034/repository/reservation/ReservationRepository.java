package org.app.g201210034.repository.reservation;

import org.app.g201210034.model.entity.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    @Query("SELECT r FROM Reservation r WHERE r.car.carId = :carId AND " +
            "(r.reservationStartDate < :endDate AND r.reservationEndDate > :startDate)")
    List<Reservation> findConflictingReservations(
            @Param("carId") Long carId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
