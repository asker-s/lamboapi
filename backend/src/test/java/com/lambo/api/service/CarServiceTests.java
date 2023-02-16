package com.lambo.api.service;


import com.lambo.api.dto.CarDto;
import com.lambo.api.dto.GarageDto;
import com.lambo.api.dto.OwnerDto;
import com.lambo.api.exceptions.GarageNotFoundException;
import com.lambo.api.exceptions.OwnerNotFoundException;
import com.lambo.api.models.Car;
import com.lambo.api.models.Garage;
import com.lambo.api.models.Owner;
import com.lambo.api.repository.CarRepository;
import com.lambo.api.repository.GarageRepository;
import com.lambo.api.repository.OwnerRepository;
import com.lambo.api.service.impl.CarServiceImpl;
import com.lambo.api.service.impl.OwnerServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarServiceTests {

    @Mock
    private CarRepository carRepository;
    @Mock
    private GarageRepository garageRepository;
    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private CarServiceImpl carService;


    private Garage garage,garage1;
    private GarageDto garageDto;

    private Owner owner;
    private OwnerDto ownerDto;

    private Car car, car1;
    private List<Car> carList;
    private List<Garage> garageList;
    private CarDto carDto;

    @BeforeEach
    public void init() {
        owner = Owner.builder().name("Name_Test").surname("Surname_Test").id(1).build();
        ownerDto = OwnerDto.builder().name("Name_Test1").surname("Surname_Test1").build();

        garage = Garage.builder().name("Garage_Test").location("Location_Test").id(1).build();
        garage1 = Garage.builder().name("Garage_Test").location("Location_Test").id(2).build();
        garageDto = GarageDto.builder().name("Garage_Test1").location("Location_Test1").build();

        car = Car.builder().brand("Brand_Test").model("Model_Test").build();
        car1 = Car.builder().brand("Brand_Test").model("Model_Test").id(2).build();
        carDto = CarDto.builder().brand("Brand_Test").model("Model_Test").build();

        Set<Car> cars = new HashSet<>();
        Set<Car> cars1 = new HashSet<>();
        Set<Garage> garages = new HashSet<>();
        garages.add(garage);
        cars.add(car);
        cars1.add(car1);
        garage.setCars(cars);
        garage1.setCars(cars1);
        garage.setOwner(owner);
        car.setGarages(garages);

        carList = new ArrayList<>();
        carList.add(car);
        garageList = new ArrayList<>();
        garageList.add(garage);
    }

    @Test
    public void testCreateCar() {
        Car car = Car.builder().brand("Brand_Test").model("Model_Test").build();
        CarDto carDto = CarDto.builder().brand("Brand_Test").model("Model_Test").build();
        when(carRepository.save(Mockito.any(Car.class))).thenReturn(car);
        CarDto savedCar = carService.createCar(carDto);
        Assertions.assertThat(savedCar).isNotNull();
    }

    @Test
    public void testGetCarsByOwnerIdAndGarageId() {
        when(ownerRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(garageRepository.findById(garage.getId())).thenReturn(Optional.of(garage));
        List<CarDto> allCars = carService.getCarsByOwnerIdAndGarageId(owner.getId(), garage.getId());
        Assertions.assertThat(allCars).isNotNull();
    }


    @Test
    public void testRemoveCarFromGarageByOwnerIdAndGarageId() {
        when(ownerRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(garageRepository.findById(garage.getId())).thenReturn(Optional.of(garage));
        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));
        when(carRepository.save(Mockito.any(Car.class))).thenReturn(car);
        when(garageRepository.save(Mockito.any(Garage.class))).thenReturn(garage);
        assertAll(() -> carService.removeCarFromGarageByOwnerIdAndGarageId(owner.getId(), garage.getId(), car.getId()));
    }


    @Test
    public void testGetAllCars(){
        when(carRepository.findAll()).thenReturn(carList);
        List<CarDto> allCars = carService.getAllCars();
        Assertions.assertThat(allCars).isNotNull();
    }

    @Test
    public void testDeleteAllCars(){
        when(garageRepository.findAll()).thenReturn(garageList);
        when(carRepository.save(Mockito.any(Car.class))).thenReturn(car);
        when(garageRepository.save(Mockito.any(Garage.class))).thenReturn(garage);
        assertAll(()->carService.deleteAllCars());
    }

    @Test
    public void testAssignGarageToCar(){
        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));
        when(garageRepository.findById(garage1.getId())).thenReturn(Optional.of(garage1));
        when(carRepository.save(Mockito.any(Car.class))).thenReturn(car);
        when(garageRepository.save(Mockito.any(Garage.class))).thenReturn(garage1);
        CarDto savedCar = carService.assignGarageToCar(car.getId(), garage1.getId());
        Assertions.assertThat(savedCar).isNotNull();
    }
}
