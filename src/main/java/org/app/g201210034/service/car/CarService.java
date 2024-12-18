package org.app.g201210034.service.car;

import lombok.RequiredArgsConstructor;
import org.app.g201210034.model.entity.car.Car;
import org.app.g201210034.model.request.car.CarRequest;
import org.app.g201210034.model.request.file.FileRequest;
import org.app.g201210034.model.request.query.AvaliableCarRequest;
import org.app.g201210034.model.request.query.AvaliableCarRequestMax;
import org.app.g201210034.repository.car.CarRepository;
import org.app.g201210034.results.*;
import org.app.g201210034.service.file.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService implements ICarService{
    private final CarRepository carRepository;
    private final FileService fileService;

    @Override
    public DataResult<List<Car>> getAllCars() {
        List<Car> cars = carRepository.findAll();
        if(!cars.isEmpty())
            return new SuccessDataResult<>(carRepository.findAll(), Result.showMessage(Result.SUCCESS, ResultMessageType.SUCCESS, "Cars listed successfully"));
        return new ErrorDataResult<>(Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "Cars not found"));
    }

    @Override
    public DataResult<Car> getCarById(Long id) {
        if(id == null)
            return new ErrorDataResult<>(Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "Car id cannot be empty"));
        Optional<Car> car = carRepository.findById(id);
        if(car.isPresent())
            return new SuccessDataResult<>(car.get(), Result.showMessage(Result.SUCCESS, ResultMessageType.SUCCESS, "Car found successfully"));
        return new ErrorDataResult<>(Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "Car not found"));
    }

    @Override
    public Result saveCar(CarRequest carRequest) {
        Car carSaved = new Car();
        carSaved.setBrand(carRequest.getBrand());
        carSaved.setModel(carRequest.getModel());
        carSaved.setPlate(carRequest.getPlate());
        carSaved.setYear(carRequest.getYear());
        carSaved.setFuelType(carRequest.getFuelType());
        carSaved.setGearType(carRequest.getGearType());
        carSaved.setDescription(carRequest.getDescription());
        carSaved.setImageUrl(carRequest.getImageUrl());
        carSaved.setMaxLuggage(carRequest.getMaxLuggage());
        carSaved.setMaxPassenger(carRequest.getMaxPassenger());
        carSaved.setStatus(carRequest.getStatus());
        carRepository.save(carSaved);
        return Result.showMessage(Result.SUCCESS, ResultMessageType.SUCCESS, "Car saved successfully");
    }

    @Override
    public Result updateCar(Long carId, CarRequest carRequest) {
        Optional<Car> car = carRepository.findById(carId);
        if(car.isPresent()){
            Car carUpdated = car.get();
            carUpdated.setBrand(carRequest.getBrand());
            carUpdated.setModel(carRequest.getModel());
            carUpdated.setPlate(carRequest.getPlate());
            carUpdated.setYear(carRequest.getYear());
            carUpdated.setFuelType(carRequest.getFuelType());
            carUpdated.setMaxLuggage(carRequest.getMaxLuggage());
            carUpdated.setMaxPassenger(carRequest.getMaxPassenger());
            carUpdated.setGearType(carRequest.getGearType());
            carUpdated.setDescription(carRequest.getDescription());
            carUpdated.setImageUrl(carRequest.getImageUrl());
            carUpdated.setStatus(carRequest.getStatus());
            carRepository.save(carUpdated);
            return Result.showMessage(Result.SUCCESS, ResultMessageType.SUCCESS, "Car updated successfully");
        }
        return Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "Car not found or does not exist");
    }



    @Override
    public Result deleteCar(Long carId) {
        Optional<Car> car = carRepository.findById(carId);
        if(car.isPresent()){
            carRepository.deleteById(carId);
            return Result.showMessage(Result.SUCCESS, ResultMessageType.SUCCESS, "Car deleted successfully");
        }
        return Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "Car not found or does not exist");
    }

    @Override
    public DataResult<List<Car>> findAvaliableCars(AvaliableCarRequest carRequest) {

        List<Car> cars = carRepository.findAvailableCars(carRequest.getPassengerCount(), carRequest.getLuggageCount());
        if (!cars.isEmpty())
            return new SuccessDataResult<>(cars, Result.showMessage(Result.SUCCESS, ResultMessageType.SUCCESS, "Available cars listed successfully"));
        return new ErrorDataResult<>(Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "Available cars not found"));

    }



    @Override
    public DataResult<List<Car>> findAvaliableCarsMax(AvaliableCarRequestMax carRequest) {

        // Check if the startDate is before the current date
        if (carRequest.getStartDate().isBefore(LocalDateTime.now())) {
            return new ErrorDataResult<>(Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "The start date cannot be before the current date."));
        }

        // Check if the endDate is before the current date
        if (carRequest.getEndDate().isBefore(LocalDateTime.now())) {
            return new ErrorDataResult<>(Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "The end date cannot be before the current date."));
        }

        // Check if the startDate is after the endDate
        if (carRequest.getStartDate().isAfter(carRequest.getEndDate())) {
            return new ErrorDataResult<>(Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "The start date cannot be after the end date."));
        }

        List<Car> cars = carRepository.findAvailableCarsMax(carRequest.getPassengerCount(), carRequest.getLuggageCount(), carRequest.getStartDate(), carRequest.getEndDate());
        if (!cars.isEmpty())
            return new SuccessDataResult<>(cars, Result.showMessage(Result.SUCCESS, ResultMessageType.SUCCESS, "Available cars listed successfully"));
        return new ErrorDataResult<>(Result.showMessage(Result.SUCCESS_EMPTY, ResultMessageType.ERROR, "Available cars not found"));

    }

    @Override
    public Result saveCarWithPhoto(CarRequest carRequest, MultipartFile[] photos) {
        Car carNew = new Car();
        carNew.setBrand(carRequest.getBrand());
        carNew.setModel(carRequest.getModel());
        carNew.setPlate(carRequest.getPlate());
        carNew.setYear(carRequest.getYear());
        carNew.setFuelType(carRequest.getFuelType());
        carNew.setGearType(carRequest.getGearType());
        carNew.setDescription(carRequest.getDescription());
        carNew.setMaxLuggage(carRequest.getMaxLuggage());
        carNew.setMaxPassenger(carRequest.getMaxPassenger());
        carNew.setStatus(carRequest.getStatus());
        carRepository.save(carNew);
        addCarFile(carNew, photos);
        return Result.showMessage(Result.SUCCESS, ResultMessageType.SUCCESS, "Car saved successfully");
    }

    private void addCarFile(Car car, MultipartFile[] photos) {
        if (photos != null) {
            for (MultipartFile photo : photos) {
                DataResult<Boolean> fileFindByIdAndRequestId = fileService.existFindByFileNameAndCarId(car.getCarId(), photo.getOriginalFilename());
                if (fileFindByIdAndRequestId.getData() != null && !fileFindByIdAndRequestId.getData()) {
                    fileService.uploadSingleFile(photo, car.getCarId());
                    FileRequest fileRequest = new FileRequest();
                    fileRequest.setFileName(photo.getOriginalFilename());
                    fileRequest.setFileType(photo.getContentType());
                    fileRequest.setSize(photo.getSize());
                    fileRequest.setFileDownloadUri(photo.getOriginalFilename() + "?carId=" + car.getCarId());
                    fileRequest.setCarId(car.getCarId());
                    fileService.saveFile(fileRequest);
                }

            }
        }
    }


}
