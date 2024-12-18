package org.app.g201210034.service.reservation;

import org.app.g201210034.model.entity.reservation.Reservation;
import org.app.g201210034.model.request.reservation.ReservationRequest;
import org.app.g201210034.results.DataResult;
import org.app.g201210034.results.Result;

import java.util.List;

public interface IReservationService {

    DataResult<List<Reservation>> getAllReservations();

    DataResult<Reservation> getReservationById(Long id);

    //DataResult<Reservation> getReservationByNumber(String reservationNumber);

    Result saveReservation(ReservationRequest reservationRequest);

    Result updateReservation(Long reservationId, ReservationRequest reservationRequest);

    Result deleteReservation(Long reservationId);
}
