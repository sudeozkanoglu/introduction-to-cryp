package org.app.g201210034.service.car;

import org.app.g201210034.model.entity.car.Car;
import org.app.g201210034.model.request.car.CarRequest;
import org.app.g201210034.model.request.query.AvaliableCarRequest;
import org.app.g201210034.model.request.query.AvaliableCarRequestMax;
import org.app.g201210034.results.DataResult;
import org.app.g201210034.results.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICarService {

    DataResult<List<Car>> getAllCars();

    DataResult<Car> getCarById(Long id);

    //DataResult<Car> getCarByPlate(String reservationNumber);

    Result saveCar(CarRequest carRequest);

    Result updateCar(Long carId, CarRequest carRequest);

    Result deleteCar(Long carId);

    DataResult<List<Car>> findAvaliableCars(AvaliableCarRequest carRequest);

    DataResult<List<Car>> findAvaliableCarsMax(AvaliableCarRequestMax carRequest);

    Result saveCarWithPhoto(CarRequest carRequest, MultipartFile[] photos);
}
