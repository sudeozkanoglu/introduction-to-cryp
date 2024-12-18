package org.app.g201210034.controller.reservation;

import lombok.RequiredArgsConstructor;
import org.app.g201210034.model.entity.reservation.Reservation;
import org.app.g201210034.model.request.reservation.ReservationRequest;
import org.app.g201210034.results.DataResult;
import org.app.g201210034.results.Result;
import org.app.g201210034.service.reservation.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/reservation/v1")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping(path = "getAllReservations")
    public DataResult<List<Reservation>> getAllReservations(){
        return reservationService.getAllReservations();
    }

    @GetMapping(path = "getReservationById")
    public DataResult<Reservation> getReservationById(@RequestParam Long id){
        return reservationService.getReservationById(id);
    }

    @PostMapping(path = "saveReservation")
    public Result saveReservation(@RequestBody ReservationRequest reservationRequest){ return reservationService.saveReservation(reservationRequest);}

    @PostMapping(path = "updateReservation")
    public Result updateReservation(@RequestParam Long reservationId, @RequestBody ReservationRequest reservationRequest){ return reservationService.updateReservation(reservationId, reservationRequest);}

    @PostMapping(path = "deleteReservation")
    public Result deleteReservation(@RequestParam Long reservationId){ return reservationService.deleteReservation(reservationId);}




}
