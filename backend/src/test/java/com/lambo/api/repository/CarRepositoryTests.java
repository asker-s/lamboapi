package com.lambo.api.repository;

import com.lambo.api.models.Car;
import com.lambo.api.models.Garage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CarRepositoryTests {
    private CarRepository carRepository;

    @Autowired
    public CarRepositoryTests(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Test
    public void testSaveAll() {
        Car car = Car.builder().brand("Brand_Test").model("Model_Test").build();

        Car savedCar = carRepository.save(car);

        Assertions.assertThat(savedCar).isNotNull();
        Assertions.assertThat(savedCar.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetAll() {
        Car car = Car.builder().brand("Brand_Test").model("Model_Test").build();
        Car car1 = Car.builder().brand("Brand_Test1").model("Model_Test1").build();
        Car car2 = Car.builder().brand("Brand_Test2").model("Model_Test2").build();

        carRepository.save(car);
        carRepository.save(car1);
        carRepository.save(car2);

        List<Car> carList = carRepository.findAll();

        Assertions.assertThat(carList).isNotNull();
        Assertions.assertThat(carList.size()).isEqualTo(3);
    }

    @Test
    public void testFindById() {
        Car car = Car.builder().brand("Brand_Test").model("Model_Test").build();

        carRepository.save(car);

        Car carReturn = carRepository.findById(car.getId()).get();

        Assertions.assertThat(carReturn).isNotNull();
    }

    @Test
    public void testUpdateCar() {
        Car car = Car.builder().brand("Brand_Test").model("Model_Test").build();

        carRepository.save(car);

        Car carSave = carRepository.findById(car.getId()).get();
        carSave.setBrand("Brand_Test1");
        carSave.setModel("Model_Test1");

        Car updatedCar = carRepository.save(carSave);

        Assertions.assertThat(updatedCar.getBrand()).isNotNull();
        Assertions.assertThat(updatedCar.getModel()).isNotNull();
    }

    @Test
    public void testCarDelete() {
        Car car = Car.builder().brand("Brand_Test").model("Model_Test").build();

        carRepository.save(car);

        carRepository.deleteById(car.getId());
        Optional<Car> foundCar = carRepository.findById(car.getId());

        Assertions.assertThat(foundCar).isEmpty();
    }

}