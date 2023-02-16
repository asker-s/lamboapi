package com.lambo.api.repository;

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
public class OwnerRepositoryTests {

    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    public void testSaveAll() {
        Owner owner = Owner.builder().name("Name_Test").surname("Surname_Test").build();

        Owner savedOwner = ownerRepository.save(owner);

        Assertions.assertThat(savedOwner).isNotNull();
        Assertions.assertThat(savedOwner.getId()).isGreaterThan(0);

    }

    @Test
    public void testGetAll() {
        Owner owner = Owner.builder().name("Name_Test").surname("Surname_Test").build();

        Owner owner1 = Owner.builder().name("Name_Test1").surname("Surname_Test1").build();

        ownerRepository.save(owner);
        ownerRepository.save(owner1);

        List<Owner> ownerList = ownerRepository.findAll();

        Assertions.assertThat(ownerList).isNotNull();
        Assertions.assertThat(ownerList.size()).isEqualTo(2);
    }

    @Test
    public void testFindById() {
        Owner owner = Owner.builder().name("Name_Test").surname("Surname_Test").build();

        ownerRepository.save(owner);

        Owner foundOwner = ownerRepository.findById(owner.getId()).get();

        Assertions.assertThat(foundOwner).isNotNull();
    }

    @Test
    public void testFindByName() {
        Owner owner = Owner.builder().name("Name_Test").surname("Surname_Test").build();

        ownerRepository.save(owner);

        Owner foundOwner = ownerRepository.findByName(owner.getName()).get();

        Assertions.assertThat(foundOwner).isNotNull();
    }

    @Test
    public void testUpdateOwner() {
        Owner owner = Owner.builder().name("Name_Test").surname("Surname_Test").build();

        ownerRepository.save(owner);

        Owner foundOwner = ownerRepository.findById(owner.getId()).get();
        foundOwner.setName("Name_Test1");
        foundOwner.setSurname("Surname_Test1");

        Owner updatedOwner = ownerRepository.save(foundOwner);

        Assertions.assertThat(updatedOwner.getName()).isNotNull();
        Assertions.assertThat(updatedOwner.getSurname()).isNotNull();
    }

    @Test
    public void testOwnerDelete() {
        Owner owner = Owner.builder().name("Name_Test").surname("Surname_Test").build();

        ownerRepository.save(owner);

        ownerRepository.deleteById(owner.getId());
        Optional<Owner> foundOwner = ownerRepository.findById(owner.getId());

        Assertions.assertThat(foundOwner).isEmpty();
    }

}
