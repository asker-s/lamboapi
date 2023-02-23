package com.lambo.api.repository;

import com.lambo.api.models.Owner;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class OwnerRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OwnerRepository ownerRepository;


    @Test
    public void testFindByName() {
        Owner owner = Owner.builder().name("Name_Test").surname("Surname_Test").build();
        entityManager.persist(owner);
        entityManager.flush();

        Owner foundOwner = ownerRepository.findByName(owner.getName()).get();

        Assertions.assertThat(foundOwner.getName()).isEqualTo("Name_Test");
        Assertions.assertThat(foundOwner.getSurname()).isEqualTo("Surname_Test");
    }

}
