package com.lambo.api.service.impl;

import com.lambo.api.dto.CarDto;
import com.lambo.api.dto.CarResponse;
import com.lambo.api.dto.GarageDto;
import com.lambo.api.dto.OwnerResponse;
import com.lambo.api.exceptions.CarNotFoundException;
import com.lambo.api.exceptions.GarageNotFoundException;
import com.lambo.api.exceptions.OwnerNotFoundException;
import com.lambo.api.models.Car;
import com.lambo.api.models.Garage;
import com.lambo.api.models.Owner;
import com.lambo.api.repository.CarRepository;
import com.lambo.api.repository.GarageRepository;
import com.lambo.api.repository.OwnerRepository;
import com.lambo.api.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class CarServiceImpl implements CarService {
    private CarRepository carRepository;
    private GarageRepository garageRepository;
    private OwnerRepository ownerRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, GarageRepository garageRepository, OwnerRepository ownerRepository) {
        this.carRepository = carRepository;
        this.garageRepository = garageRepository;
        this.ownerRepository = ownerRepository;
    }

    //works fine
    @Override
    public CarDto createCar(CarDto carDto) {
        Car car = mapToEntity(carDto);
        Car newCar = carRepository.save(car);
        return mapToDto(newCar);
    }

    //works fine
    @Override
    public List<CarDto> getCarsByOwnerIdAndGarageId(int ownerId, int garageId) {

        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new OwnerNotFoundException("Owner with associated garage not found"));

        Garage garage = garageRepository.findById(garageId).orElseThrow(() -> new GarageNotFoundException("Garage with associated owner not found"));

        if (garage.getOwner().getId() != owner.getId()) {
            throw new GarageNotFoundException("This owner is not the owner of the garage");
        }

        Set<Car> cars = garage.getCars();

        return cars.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    //works fine
    @Override
    public void removeCarFromGarageByOwnerIdAndGarageId(int ownerId, int garageId, int carId) {

        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new OwnerNotFoundException("Owner with associated garage not found"));

        Garage garage = garageRepository.findById(garageId).orElseThrow(() -> new GarageNotFoundException("Garage with associated owner not found"));

        if (garage.getOwner().getId() != owner.getId()) {
            throw new GarageNotFoundException("This owner is not the owner of the garage");
        }

        Car car = carRepository.findById(carId).orElseThrow((() -> new CarNotFoundException("Car with associated garage does not exist")));

        garage.removeCar(car);
        garageRepository.save(garage);
        carRepository.save(car);

        int count = 0;
        List<Garage> garageList = garageRepository.findAll();
        for(Garage garage1: garageList) {
            if (garage1.getCars().contains(car)) {
                count++;
            }
        }
        if (count == 0) {
            carRepository.delete(car);
        }
    }

    //works fine
    @Override
    public List<CarDto> getAllCars() {
        List<Car> cars = carRepository.findAll();

        return cars.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    //works finez
    @Override
    public void deleteAllCars() {
        List<Garage> garages = garageRepository.findAll();
        for (Garage garage : garages) {
            for (Car car: garage.getCars()){
                car.removeGarage(garage);
                carRepository.save(car);
            }
            garageRepository.save(garage);
        }
        carRepository.deleteAll();
    }

    @Override
    public CarDto assignGarageToCar(int carId, int garageId) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new CarNotFoundException("Car does not exist"));
        Garage garage = garageRepository.findById(garageId).orElseThrow(() -> new GarageNotFoundException("Garage does not exist"));

        if (garage.getCars().contains(car)) {
            throw new RuntimeException("This car already has access to the garage");
        }

        car.addGarage(garage);
        carRepository.save(car);
        garageRepository.save(garage);
        return mapToDto(car);
    }

    private CarDto mapToDto(Car car) {
        CarDto carDto = new CarDto();
        carDto.setId(car.getId());
        carDto.setBrand(car.getBrand());
        carDto.setModel(car.getModel());
        return carDto;
    }

    private Car mapToEntity (CarDto carDto){
        Car car = new Car();
        car.setId(carDto.getId());
        car.setBrand(carDto.getBrand());
        car.setModel(carDto.getModel());
        return car;
    }
}
