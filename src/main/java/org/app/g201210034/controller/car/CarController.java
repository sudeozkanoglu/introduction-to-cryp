package org.app.g201210034.controller.car;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.app.g201210034.model.entity.car.Car;
import org.app.g201210034.model.request.car.CarRequest;
import org.app.g201210034.model.request.query.AvaliableCarRequest;
import org.app.g201210034.model.request.query.AvaliableCarRequestMax;
import org.app.g201210034.results.DataResult;
import org.app.g201210034.results.Result;
import org.app.g201210034.service.car.CarService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/car/v1")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;
    private final ObjectMapper objectMapper;

    @GetMapping(path = "getAllCars")
    public DataResult<List<Car>> getAllCars(){
        return carService.getAllCars();
    }

    @PostMapping(path = "saveCar")
    public Result saveCar(@RequestBody CarRequest carRequest){ return carService.saveCar(carRequest);}

    @PostMapping(path = "saveCarWithPhoto")
    public Result saveCarWithPhoto(@RequestParam String carData, @RequestParam MultipartFile[] photos){
        CarRequest carRequest = mapCarData(carData);
        return carService.saveCarWithPhoto(carRequest, photos);
    }

    private CarRequest mapCarData(String carData) {
        CarRequest carRequest;
        try {
            carRequest = objectMapper.readValue(carData, CarRequest.class);
        } catch (IOException e) {
            throw new RuntimeException("Answer Mapper Exception hatası alındı!", e);
        }
        return carRequest;
    }

    @GetMapping(path = "getCarById")
    public DataResult<Car> getCarById(@RequestParam Long id){
        return carService.getCarById(id);
    }

    @PostMapping(path = "updateCar")
    public Result updateCar(@RequestParam Long carId, @RequestBody CarRequest carRequest){ return carService.updateCar(carId, carRequest);}

    @PostMapping(path = "deleteCar")
    public Result deleteCar(@RequestParam Long carId){ return carService.deleteCar(carId);}

    @GetMapping(path = "findAvaliableCars")
    public DataResult<List<Car>> findAvaliableCars(@RequestBody AvaliableCarRequest carRequest){
        return carService.findAvaliableCars(carRequest);
    }

    @GetMapping(path = "findAvaliableCarsMax")
    public DataResult<List<Car>> findAvaliableCarsMax(@RequestBody AvaliableCarRequestMax carRequest){
        return carService.findAvaliableCarsMax(carRequest);
    }




}
