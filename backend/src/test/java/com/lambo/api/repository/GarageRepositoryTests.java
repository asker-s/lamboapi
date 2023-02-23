package com.lambo.api.repository;

import com.lambo.api.models.Garage;
import com.lambo.api.models.Owner;
import org.assertj.core.api.Assertions;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class GarageRepositoryTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GarageRepository garageRepository;

    @Test
    public void testFindByOwnerId() {
        Owner owner = Owner.builder().name("Owner_Test").surname("Surname_Test").build();
        entityManager.persist(owner);
        Garage garage = Garage.builder().name("Garage_Test").location("Location_Test").build();
        garage.setOwner(owner);
        entityManager.persist(garage);
        entityManager.flush();

        List<Garage> garageList = garageRepository.findByOwnerId(owner.getId());

        Assertions.assertThat(garageList.get(0).getName()).isEqualTo("Garage_Test");
        Assertions.assertThat(garageList.get(0).getLocation()).isEqualTo("Location_Test");
    }
}
