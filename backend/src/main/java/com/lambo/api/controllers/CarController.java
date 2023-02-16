package com.lambo.api.controllers;

import com.lambo.api.dto.CarDto;
import com.lambo.api.dto.CarResponse;
import com.lambo.api.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@CrossOrigin("http://localhost:3000")
public class CarController {

    private CarService carService;

    @Autowired
    public CarController(CarService carService) { this.carService = carService; }

    //works fine, frontend completed
    @PostMapping("car/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CarDto> createCar(@RequestBody CarDto carDto) {
        return new ResponseEntity<>(carService.createCar(carDto), HttpStatus.CREATED);
    }

    //works fine, frontend completed
    @GetMapping("cars")
    public List<CarDto> getAllCars(
    ) {
        return carService.getAllCars();
    }

    //works fine, frontend implemented
    @GetMapping("owner/{ownerId}/garages/{garageId}/cars")
    public List<CarDto> getCarsByOwnerIdAndGarageId(@PathVariable(value = "ownerId") int ownerId, @PathVariable(value = "garageId") int garageId) {
        return carService.getCarsByOwnerIdAndGarageId(ownerId, garageId);
    }

    //works fine, frontend implemented
    @DeleteMapping("owner/{ownerId}/garages/{garageId}/cars/{carId}")
    public ResponseEntity<String> removeCarFromGarageByOwnerIdAndGarageId(@PathVariable(value = "ownerId") int ownerId,
                                                                @PathVariable(value = "garageId") int garageId,
                                                                @PathVariable(value = "carId") int carId) {
        carService.removeCarFromGarageByOwnerIdAndGarageId(ownerId, garageId, carId);
        return new ResponseEntity<>("Car deleted successfully", HttpStatus.OK);
    }

    //works fine, frontend completed
    @DeleteMapping("cars/delete")
    public ResponseEntity<String> deleteAllCars() {
        carService.deleteAllCars();
        return new ResponseEntity<>("All cars have been deleted", HttpStatus.OK);
    }

    //works fine, frontend implemented
    @PutMapping("cars/{carId}/garages/{garageId}/assign")
    public ResponseEntity<CarDto> assignGarageToCar(@PathVariable(value = "carId") int carId,
                                                       @PathVariable(value = "garageId") int garageId) {
        return new ResponseEntity<>(carService.assignGarageToCar(carId, garageId), HttpStatus.OK);
    }

}
