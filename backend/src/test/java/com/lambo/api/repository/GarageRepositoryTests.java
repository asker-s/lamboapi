package com.lambo.api.repository;

import com.lambo.api.models.Garage;
import com.lambo.api.models.Owner;
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
public class GarageRepositoryTests {
    private GarageRepository garageRepository;

    @Autowired
    public GarageRepositoryTests(GarageRepository garageRepository) {
        this.garageRepository = garageRepository;
    }

    @Test
    public void testSaveAll() {
        Garage garage = Garage.builder().name("Garage_Test").location("Location_Test").build();

        Garage savedGarage = garageRepository.save(garage);

        Assertions.assertThat(savedGarage).isNotNull();
        Assertions.assertThat(savedGarage.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetAll() {
        Garage garage = Garage.builder().name("Garage_Test").location("Location_Test").build();
        Garage garage1 = Garage.builder().name("Garage_Test1").location("Location_Test1").build();
        Garage garage2 = Garage.builder().name("Garage_Test2").location("Location_Test2").build();

        garageRepository.save(garage);
        garageRepository.save(garage1);
        garageRepository.save(garage2);

        List<Garage> garageList = garageRepository.findAll();


        Assertions.assertThat(garageList).isNotNull();
        Assertions.assertThat(garageList.size()).isEqualTo(3);
    }

    @Test
    public void testFindById() {
        Garage garage = Garage.builder().name("Garage_Test").location("Location_Test").build();

        garageRepository.save(garage);

        Garage garageReturn = garageRepository.findById(garage.getId()).get();

        Assertions.assertThat(garageReturn).isNotNull();
    }

    @Test
    public void testUpdateGarage() {
        Garage garage = Garage.builder().name("Garage_Test").location("Location_Test").build();

        garageRepository.save(garage);

        Garage garageSave = garageRepository.findById(garage.getId()).get();
        garageSave.setName("Garage_Test1");
        garageSave.setLocation("Location_Test1");

        Garage updatedGarage = garageRepository.save(garageSave);

        Assertions.assertThat(updatedGarage.getName()).isNotNull();
        Assertions.assertThat(updatedGarage.getLocation()).isNotNull();
    }

    @Test
    public void testGarageDelete() {
        Garage garage = Garage.builder().name("Garage_Test").location("Location_Test").build();

        garageRepository.save(garage);

        garageRepository.deleteById(garage.getId());
        Optional<Garage> foundGarage = garageRepository.findById(garage.getId());

        Assertions.assertThat(foundGarage).isEmpty();
    }

}
