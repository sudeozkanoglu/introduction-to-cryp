package org.app.g201210034.repository.car;

import org.app.g201210034.model.entity.car.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("SELECT c FROM Car c WHERE c.maxPassenger >= :passengerCount AND c.maxLuggage >= :luggageCount")
    List<Car> findAvailableCars(@Param("passengerCount") int passengerCount, @Param("luggageCount") int luggageCount);

    @Query("SELECT c FROM Car c WHERE c.maxPassenger >= :passengerCount AND c.maxLuggage >= :luggageCount AND " +
            "c.carId NOT IN (SELECT r.car.carId FROM Reservation r WHERE r.reservationStartDate <= :endDate AND r.reservationEndDate >= :startDate)")
    List<Car> findAvailableCarsMax(
            @Param("passengerCount") int passengerCount,
            @Param("luggageCount") int luggageCount,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}

