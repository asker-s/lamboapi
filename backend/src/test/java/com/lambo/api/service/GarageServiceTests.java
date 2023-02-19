package com.lambo.api.service;

import com.lambo.api.dto.CarDto;
import com.lambo.api.dto.GarageDto;
import com.lambo.api.dto.OwnerDto;
import com.lambo.api.models.Car;
import com.lambo.api.models.Garage;
import com.lambo.api.models.Owner;
import com.lambo.api.repository.CarRepository;
import com.lambo.api.repository.GarageRepository;
import com.lambo.api.repository.OwnerRepository;
import com.lambo.api.service.impl.GarageServiceImpl;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Sets;
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
public class GarageServiceTests {
    @Mock
    private CarRepository carRepository;
    @Mock
    private GarageRepository garageRepository;
    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private GarageServiceImpl garageService;

    private Garage garage;
    private GarageDto garageDto;

    private Owner owner;
    private OwnerDto ownerDto;

    private Car car;
    private CarDto carDto;

    private List<Car> carList;
    private List<Garage> garageList;

    @BeforeEach
    public void init() {
        owner = Owner.builder().name("Name_Test").surname("Surname_Test").id(1).build();
        ownerDto = OwnerDto.builder().name("Name_Test1").surname("Surname_Test1").build();

        garage = Garage.builder().name("Garage_Test").location("Location_Test").id(1).build();
        garageDto = GarageDto.builder().name("Garage_Test1").location("Location_Test1").build();

        car = Car.builder().brand("Brand_Test").model("Model_Test").id(1).build();
        carDto = CarDto.builder().brand("Brand_Test").model("Model_Test").build();

        Set<Car> cars = new HashSet<>();
        cars.add(car);

        Set<Garage> garages = new HashSet<>();
        garages.add(garage);

        garage.setCars(cars);
        garage.setOwner(owner);
        car.setGarages(garages);

        carList = new ArrayList<>();
        carList.add(car);
        garageList = new ArrayList<>();
        garageList.add(garage);
    }

    @Test
    public void testCreateGarage() {
        when(ownerRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(garageRepository.save(Mockito.any(Garage.class))).thenReturn(garage);

        GarageDto savedGarage = garageService.createGarage(garage.getId(), garageDto);

        Assertions.assertThat(savedGarage).isNotNull();
    }

    @Test
    public void testGetGaragesByOwnerId () {
        int ownerId = 1;
        when(garageRepository.findByOwnerId(ownerId)).thenReturn(Arrays.asList(garage));

        List<GarageDto> garageReturn = garageService.getGaragesByOwnerId(ownerId);

        Assertions.assertThat(garageReturn).isNotNull();
    }

    @Test
    public void testGetGarageById () {
        int ownerId = 1;
        int garageId = 1;

        garage.setOwner(owner);

        when(ownerRepository.findById(ownerId)).thenReturn(Optional.of(owner));
        when(garageRepository.findById(garageId)).thenReturn(Optional.of(garage));

        GarageDto garageReturn = garageService.getGarageById(garageId, ownerId);

        Assertions.assertThat(garageReturn).isNotNull();
    }

    @Test
    public void testAssignCarToGarage(){
        when(ownerRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));
        when(garageRepository.findById(garage.getId())).thenReturn(Optional.of(garage));
        when(carRepository.save(Mockito.any(Car.class))).thenReturn(car);
        when(garageRepository.save(Mockito.any(Garage.class))).thenReturn(garage);
        GarageDto savedGarage = garageService.assignCarToGarage(owner.getId(), garage.getId(), car.getId());
        Assertions.assertThat(savedGarage).isNotNull();
    }

    @Test
    public void testDeleteGarage() {
        when(ownerRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(garageRepository.findById(garage.getId())).thenReturn(Optional.of(garage));
        when(garageRepository.save(Mockito.any(Garage.class))).thenReturn(garage);
        when(carRepository.save(Mockito.any(Car.class))).thenReturn(car);
        assertAll(()->garageService.deleteGarage(owner.getId(), garage.getId()));
    }
}
