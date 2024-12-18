package org.app.g201210034.service.reservation;

import lombok.RequiredArgsConstructor;
import org.app.g201210034.model.entity.car.Car;
import org.app.g201210034.model.entity.reservation.Reservation;
import org.app.g201210034.model.request.reservation.ReservationRequest;
import org.app.g201210034.repository.reservation.ReservationRepository;
import org.app.g201210034.results.*;
import org.app.g201210034.service.car.CarService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final CarService carService;

    @Override
    public DataResult<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        if(!reservations.isEmpty())
            return new SuccessDataResult<>(reservations, Result.showMessage(Result.SUCCESS, ResultMessageType.SUCCESS, "Reservations listed successfully"));
        return new ErrorDataResult<>(Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "Reservations not found"));
    }

    @Override
    public DataResult<Reservation> getReservationById(Long id) {
        if(id == null)
            return new ErrorDataResult<>(Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "Reservation id cannot be empty"));
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if(reservation.isPresent())
            return new SuccessDataResult<>(reservation.get(), Result.showMessage(Result.SUCCESS, ResultMessageType.SUCCESS, "Reservation found successfully"));
        return new ErrorDataResult<>(Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "Reservation not found"));
    }

    @Override
    public Result saveReservation(ReservationRequest reservationRequest) {
        if(reservationRequest.getIdentityNumber().isEmpty() && reservationRequest.getEmail().isEmpty())
            return Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "Identity number and email cannot be empty");

        if(reservationRequest.getCarId() == null )
            return Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "Car id is empty or car not found");

        if(carService.getCarById(reservationRequest.getCarId()) == null)
            return Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "Car not found");
        Car car = carService.getCarById(reservationRequest.getCarId()).getData();

        List<Reservation> conflictingReservations = reservationRepository.findConflictingReservations(car.getCarId(), reservationRequest.getReservationStartDate(), reservationRequest.getReservationEndDate());
        if (!conflictingReservations.isEmpty()) {
            return Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "The car is already reserved for the given dates.");
        }

        // Check if the reservation start date is before the current date
        if (reservationRequest.getReservationStartDate().isBefore(LocalDateTime.now())) {
            return Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "The reservation start date cannot be before the current date.");
        }

        // Check if the reservation start date is after the reservation end date
        if (reservationRequest.getReservationStartDate().isAfter(reservationRequest.getReservationEndDate())) {
            return Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "The reservation start date cannot be after the reservation end date.");
        }

        Reservation reservationSaved = new Reservation();
        reservationSaved.setFirstName(reservationRequest.getFirstName());
        reservationSaved.setLastName(reservationRequest.getLastName());
        reservationSaved.setIdentityNumber(reservationRequest.getIdentityNumber());
        reservationSaved.setPhoneNumber(reservationRequest.getPhoneNumber());
        reservationSaved.setEmail(reservationRequest.getEmail());
        reservationSaved.setReservationStartDate(reservationRequest.getReservationStartDate());
        reservationSaved.setReservationEndDate(reservationRequest.getReservationEndDate());
        reservationSaved.setHour(reservationRequest.getHour());
        reservationSaved.setStartLocation(reservationRequest.getStartLocation());
        reservationSaved.setEndLocation(reservationRequest.getEndLocation());
        reservationSaved.setNumberOfPeople(reservationRequest.getNumberOfPeople());
        reservationSaved.setLuggageCount(reservationRequest.getLuggageCount());
        reservationSaved.setIsConfirmed(false);
        reservationSaved.setCreatedAt(LocalDateTime.now());
        reservationSaved.setCar(car);
        reservationRepository.save(reservationSaved);
        return Result.showMessage(Result.SUCCESS, ResultMessageType.SUCCESS, "Reservation saved successfully");

    }

    @Override
    public Result updateReservation(Long reservationId, ReservationRequest reservationRequest) {
        if (reservationId == null)
            return Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "Reservation id cannot be empty");

        if (reservationRequest.getIdentityNumber().isEmpty() && reservationRequest.getEmail().isEmpty())
            return Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "Identity number and email cannot be empty");

        if(reservationRequest.getCarId() == null )
            return Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "Car id is empty");

        if(carService.getCarById(reservationRequest.getCarId()) == null)
            return Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "Car not found");

        Car car = carService.getCarById(reservationRequest.getCarId()).getData();
        if(car == null)
            return Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "Car not found");


        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if(reservation.isPresent()){
            Reservation reservationUpdated = reservation.get();
            reservationUpdated.setFirstName(reservationRequest.getFirstName());
            reservationUpdated.setLastName(reservationRequest.getLastName());
            reservationUpdated.setIdentityNumber(reservationRequest.getIdentityNumber());
            reservationUpdated.setPhoneNumber(reservationRequest.getPhoneNumber());
            reservationUpdated.setEmail(reservationRequest.getEmail());
            reservationUpdated.setReservationStartDate(reservationRequest.getReservationStartDate());
            reservationUpdated.setReservationEndDate(reservationRequest.getReservationEndDate());
            reservationUpdated.setHour(reservationRequest.getHour());
            reservationUpdated.setStartLocation(reservationRequest.getStartLocation());
            reservationUpdated.setNumberOfPeople(reservationRequest.getNumberOfPeople());
            reservationUpdated.setLuggageCount(reservationRequest.getLuggageCount());
            reservationUpdated.setEndLocation(reservationRequest.getEndLocation());
            reservationUpdated.setUpdatedAt(LocalDateTime.now());
            reservationUpdated.setIsConfirmed(reservationRequest.getIsConfirmed());
            reservationUpdated.setCar(car);
            reservationRepository.save(reservationUpdated);
            return Result.showMessage(Result.SUCCESS, ResultMessageType.SUCCESS, "Reservation updated successfully");
        }
        return Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "Reservation not found");

    }

    @Override
    public Result deleteReservation(Long reservationId) {
        if(reservationId == null)
            return Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "Reservation id cannot be empty");

        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if(reservation.isPresent()) {
            reservationRepository.deleteById(reservationId);
            return Result.showMessage(Result.SUCCESS, ResultMessageType.SUCCESS, "Reservation deleted successfully");
        }
        return Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "Reservation not found");

    }
}
