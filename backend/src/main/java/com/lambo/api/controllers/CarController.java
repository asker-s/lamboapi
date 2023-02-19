package com.lambo.api.controllers;

import com.lambo.api.dto.CarDto;
import com.lambo.api.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Cars Controller")
@RestController
@RequestMapping("/api/")
@CrossOrigin("http://localhost:3000")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    //works fine, frontend completed

    @Operation(
            operationId = "createCar",
            summary = "Creates a new car"
    )
    @ApiResponse(responseCode = "201", description = "The car is created",
            content = @Content(schema = @Schema(implementation = CarDto.class))
    )
    @PostMapping("car")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CarDto> createCar(@RequestBody CarDto carDto) {
        return new ResponseEntity<>(carService.createCar(carDto), HttpStatus.CREATED);
    }

    @Operation(
            operationId = "getAllCars",
            summary = "Retrieves all the cars in the system"
    )
    //works fine, frontend completed
    @GetMapping("car")
    public List<CarDto> getAllCars() {
        return carService.getAllCars();
    }

    @Operation(operationId = "getCarsByOwnerIdAndGarageId",
            summary = "Retrieves all the cars owned by an owner",
            parameters = {
                    @Parameter(name = "ownerId", schema = @Schema(implementation = Integer.class), description = "owner id"),
                    @Parameter(name = "garageId", schema = @Schema(implementation = Integer.class), description = "garage id")
            })
    //works fine, frontend implemented
    @GetMapping("owner/{ownerId}/garages/{garageId}/cars")
    public List<CarDto> getCarsByOwnerIdAndGarageId(@PathVariable(value = "ownerId") int ownerId, @PathVariable(value = "garageId") int garageId) {
        return carService.getCarsByOwnerIdAndGarageId(ownerId, garageId);
    }

    @Operation(operationId = "deleteCarFromGarageByOwnerIdAndGarageId",
            summary = "Deletes a car from the garage by the owner id and garage id",
            parameters = {
                    @Parameter(name = "ownerId", schema = @Schema(implementation = Integer.class), description = "owner id"),
                    @Parameter(name = "garageId", schema = @Schema(implementation = Integer.class), description = "garage id"),
                    @Parameter(name = "cardId", schema = @Schema(implementation = Integer.class), description = "car id")
            })
    //works fine, frontend implemented
    @DeleteMapping("owner/{ownerId}/garages/{garageId}/cars/{carId}")
    public ResponseEntity<String> removeCarFromGarageByOwnerIdAndGarageId(@PathVariable(value = "ownerId") int ownerId,
                                                                          @PathVariable(value = "garageId") int garageId,
                                                                          @PathVariable(value = "carId") int carId) {
        carService.removeCarFromGarageByOwnerIdAndGarageId(ownerId, garageId, carId);
        return new ResponseEntity<>("Car deleted successfully", HttpStatus.OK);
    }

    @Operation(operationId = "deleteAllCars", summary = "Deletes all cars")
    //works fine, frontend completed
    @DeleteMapping("car")
    public ResponseEntity<String> deleteAllCars() {
        carService.deleteAllCars();
        return new ResponseEntity<>("All cars have been deleted", HttpStatus.OK);
    }

    @Operation(operationId = "assignGarageToCar",
            summary = "Assigns a garage to car",
            parameters = {
                    @Parameter(name = "garageId", schema = @Schema(implementation = Integer.class), description = "garage id"),
                    @Parameter(name = "cardId", schema = @Schema(implementation = Integer.class), description = "car id")
            })

    //works fine, frontend implemented
    @PutMapping("car/{carId}/garages/{garageId}")
    public ResponseEntity<CarDto> assignGarageToCar(@PathVariable(value = "carId") int carId,
                                                    @PathVariable(value = "garageId") int garageId) {
        return new ResponseEntity<>(carService.assignGarageToCar(carId, garageId), HttpStatus.OK);
    }

}
