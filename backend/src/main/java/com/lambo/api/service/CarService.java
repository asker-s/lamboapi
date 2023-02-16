package com.lambo.api.service;

import com.lambo.api.dto.CarDto;
import com.lambo.api.dto.CarResponse;
import com.lambo.api.dto.GarageDto;

import java.util.List;

public interface CarService {
    CarDto createCar(CarDto carDto);
    List<CarDto> getCarsByOwnerIdAndGarageId(int ownerId, int garageId);
    void removeCarFromGarageByOwnerIdAndGarageId(int ownerId, int garageId, int carId);
    List<CarDto> getAllCars();
    void deleteAllCars();
    CarDto assignGarageToCar (int carId, int garageId);
}
