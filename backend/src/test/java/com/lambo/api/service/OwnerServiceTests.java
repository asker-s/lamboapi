package com.lambo.api.service;


import com.lambo.api.dto.OwnerDto;
import com.lambo.api.models.Owner;
import com.lambo.api.repository.OwnerRepository;
import com.lambo.api.service.impl.OwnerServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OwnerServiceTests {

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private OwnerServiceImpl ownerService;

    @Test
    public void testCreateOwner() {
        Owner owner = Owner.builder().name("Name_Test").surname("Surname_Test").build();

        OwnerDto ownerDto = OwnerDto.builder().name("Name_Test").surname("Surname_Test").build();

        when(ownerRepository.save(Mockito.any(Owner.class))).thenReturn(owner);

        OwnerDto savedOwner = ownerService.createOwner(ownerDto);

        Assertions.assertThat(savedOwner).isNotNull();

    }

    @Test
    public void testGetAllOwner() {
        List<Owner> ownerList = Mockito.mock(List.class);

        when(ownerRepository.findAll()).thenReturn(ownerList);

        List<OwnerDto> ownerDtoList = ownerService.getAllOwners();

        Assertions.assertThat(ownerDtoList).isNotNull();

    }

    @Test
    public void testGetOwnerById() {
        Owner owner = Owner.builder().name("Name_Test").surname("Surname_Test").build();

        when(ownerRepository.findById(1)).thenReturn(Optional.ofNullable(owner));

        OwnerDto savedOwner = ownerService.getOwnerByID(1);

        Assertions.assertThat(savedOwner).isNotNull();

    }

    @Test
    public void testUpdateOwner() {
        Owner owner = Owner.builder().name("Name_Test").surname("Surname_Test").build();

        OwnerDto ownerDto = OwnerDto.builder().name("Name_Test1").surname("Surname_Test1").build();


        when(ownerRepository.findById(1)).thenReturn(Optional.ofNullable(owner));
        when(ownerRepository.save(Mockito.any(Owner.class))).thenReturn(owner);

        OwnerDto savedOwner = ownerService.updateOwner(ownerDto, 1);

        Assertions.assertThat(savedOwner).isNotNull();

    }

    @Test
    public void testDeleteOwnerById() {
        Owner owner = Owner.builder().name("Name_Test").surname("Surname_Test").build();

        when(ownerRepository.findById(1)).thenReturn(Optional.ofNullable(owner));

        assertAll(() -> ownerService.deleteOwnerByID(1));
    }

}
