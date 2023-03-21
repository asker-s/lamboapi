package com.lambo.api.service.impl;

import com.lambo.api.dto.*;
import com.lambo.api.exceptions.CarNotFoundException;
import com.lambo.api.exceptions.GarageExistsException;
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


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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

    //works fine
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

    @Override
    public CarDto getGarageIfCarHasAccessTo(int carId, int garageId) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new CarNotFoundException("Car does not exist"));
        Garage garage = garageRepository.findById(garageId).orElseThrow(() -> new GarageNotFoundException("Garage does not exist"));

        if (!garage.getCars().contains(car)) {
            //throw new RuntimeException("This car already has access to the garage");
            throw new GarageExistsException("Garage does not have the access for the car");
        }

        return mapToDto(car);
    }

    private CarDto mapToDto(Car car) {
        CarDto carDto = new CarDto();
        carDto.setId(car.getId());
        carDto.setBrand(car.getBrand());
        carDto.setModel(car.getModel());
        carDto.setGarageSet(Optional.ofNullable(car.getGarages()).orElse(new HashSet<>()).stream().map(garage -> {
            GarageDto garageDto = new GarageDto();
            garageDto.setId(garage.getId());
            garageDto.setName(garage.getName());
            garageDto.setLocation(garage.getLocation());

            return garageDto;
        }).collect(Collectors.toSet()));
        return carDto;
    }

    private Car mapToEntity (CarDto carDto){
        Car car = new Car();
        car.setId(carDto.getId());
        car.setBrand(carDto.getBrand());
        car.setModel(carDto.getModel());
        car.setGarages(Optional.ofNullable(carDto.getGarageSet()).orElse(new HashSet<>()).stream().map(garageDto -> {
            Garage garage = new Garage();
            garage.setId(garageDto.getId());
            garage.setName(garageDto.getName());
            garage.setLocation(garageDto.getLocation());
            return garage;
        }).collect(Collectors.toSet()));
        return car;
    }
}
