package com.lambo.api.service.impl;

import com.lambo.api.dto.OwnerDto;
import com.lambo.api.dto.OwnerResponse;
import com.lambo.api.exceptions.OwnerNotFoundException;
import com.lambo.api.models.Owner;
import com.lambo.api.repository.OwnerRepository;
import com.lambo.api.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OwnerServiceImpl implements OwnerService {
    private OwnerRepository ownerRepository;

    @Autowired
    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public OwnerDto createOwner(OwnerDto ownerDto) {
        Owner owner = new Owner();
        owner.setName(ownerDto.getName());
        owner.setSurname(ownerDto.getSurname());

        Owner newOwner = ownerRepository.save(owner);

        OwnerDto ownerResponse = new OwnerDto();
        ownerResponse.setId(newOwner.getId());
        ownerResponse.setName(newOwner.getName());
        ownerResponse.setSurname(newOwner.getSurname());

        return ownerResponse;
    }

    @Override
    public List<OwnerDto> getAllOwners() {
        List<Owner> ownerList = ownerRepository.findAll();

        return ownerList.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public OwnerDto getOwnerByID(int id) {
        Owner owner = ownerRepository.findById(id).orElseThrow(() -> new OwnerNotFoundException("Owner could not be found"));
        return mapToDto(owner);
    }

    @Override
    public OwnerDto updateOwner(OwnerDto ownerDto, int id) {
        Owner owner = ownerRepository.findById(id).orElseThrow(() -> new OwnerNotFoundException("Owner could not be updated"));

        owner.setName(ownerDto.getName());
        owner.setSurname(ownerDto.getSurname());

        Owner updatedOwner = ownerRepository.save(owner);
        return mapToDto(updatedOwner);
    }

    @Override
    public void deleteOwnerByID(int id) {
        Owner owner = ownerRepository.findById(id).orElseThrow(() -> new OwnerNotFoundException("Owner could not be deleted"));
        ownerRepository.delete(owner);
    }

    private OwnerDto mapToDto(Owner owner) {
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setId(owner.getId());
        ownerDto.setName(owner.getName());
        ownerDto.setSurname(owner.getSurname());
        return ownerDto;
    }

    private Owner mapToEntity(OwnerDto ownerDto) {
        Owner owner = new Owner();
        owner.setId(ownerDto.getId());
        owner.setName(ownerDto.getName());
        owner.setSurname(ownerDto.getSurname());
        return owner;
    }
}
