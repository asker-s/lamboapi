package com.lambo.api.service.impl;

import com.lambo.api.dto.GarageDto;
import com.lambo.api.exceptions.CarNotFoundException;
import com.lambo.api.exceptions.GarageNotFoundException;
import com.lambo.api.exceptions.OwnerNotFoundException;
import com.lambo.api.models.Car;
import com.lambo.api.models.Garage;
import com.lambo.api.models.Owner;
import com.lambo.api.repository.CarRepository;
import com.lambo.api.repository.GarageRepository;
import com.lambo.api.repository.OwnerRepository;
import com.lambo.api.service.GarageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GarageServiceImpl implements GarageService {
    private GarageRepository garageRepository;
    private OwnerRepository ownerRepository;
    private CarRepository carRepository;

    @Autowired
    public GarageServiceImpl(GarageRepository garageRepository, OwnerRepository ownerRepository, CarRepository carRepository) {
        this.garageRepository = garageRepository;
        this.ownerRepository = ownerRepository;
        this.carRepository = carRepository;
    }

    //works fine
    @Override
    public GarageDto createGarage(int ownerId, GarageDto garageDto) {
        Garage garage = mapToEntity(garageDto);

        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new OwnerNotFoundException("Owner with associated garage not found"));

        garage.setOwner(owner);

        Garage newGarage = garageRepository.save(garage);

        return mapToDto(newGarage);
    }

    //works fine
    @Override
    public List<GarageDto> getGaragesByOwnerId(int id) {
        List<Garage> garages = garageRepository.findByOwnerId(id);
        return garages.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    //works fine
    @Override
    public GarageDto getGarageById(int garageId, int ownerId) {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new OwnerNotFoundException("Owner with associated garage not found"));

        Garage garage = garageRepository.findById(garageId).orElseThrow(() -> new GarageNotFoundException("Garage with associated owner not found"));

        if (garage.getOwner().getId() != owner.getId()) {
            throw new GarageNotFoundException("This owner is not the owner of the garage");
        }

        return mapToDto(garage);
    }

    //works fine
    @Override
    public void deleteGarage(int ownerId, int garageId) {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new OwnerNotFoundException("Owner with associated garage not found"));

        Garage garage = garageRepository.findById(garageId).orElseThrow(() -> new GarageNotFoundException("Garage with associated owner not found"));

        if (garage.getOwner().getId() != owner.getId()) {
            throw new GarageNotFoundException("This owner is not the owner of the garage");
        }

        Set<Car> cars = garage.getCars();
        for (Car car: cars){
            car.removeGarage(garage);
            carRepository.save(car);
        }
        garageRepository.save(garage);
        garageRepository.delete(garage);
    }

    //works fine
    @Override
    public GarageDto assignCarToGarage(int ownerId, int garageId, int carId) {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(()-> new OwnerNotFoundException("Owner does not exist"));
        Garage garage = garageRepository.findById(garageId).orElseThrow(()-> new GarageNotFoundException("Garage does not exist"));

        if (garage.getOwner().getId() != owner.getId()) {
            throw new RuntimeException("Garage with associated owner does not exist");
        }

        Car car = carRepository.findById(carId).orElseThrow(() -> new CarNotFoundException("Car does not exist"));

        garage.addCar(car);

        garageRepository.save(garage);
        carRepository.save(car);
        return mapToDto(garage);
    }

    //works fine
    @Override
    public List<GarageDto> getGaragesCarHasAccessTo(int carId) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new CarNotFoundException("Car does not exist"));
        List<Garage> garages = car.getGarages().stream().toList();
        return garages.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private GarageDto mapToDto(Garage garage) {
        GarageDto garageDto = new GarageDto();
        garageDto.setId(garage.getId());
        garageDto.setName(garage.getName());
        garageDto.setLocation(garage.getLocation());
        return garageDto;
    }

    private Garage mapToEntity(GarageDto garageDto) {
        Garage garage = new Garage();
        garage.setId(garageDto.getId());
        garage.setName(garageDto.getName());
        garage.setLocation(garageDto.getLocation());
        return garage;
    }
}
