package com.lambo.api.service;

import com.lambo.api.dto.OwnerDto;
import com.lambo.api.dto.OwnerResponse;

import java.util.List;

public interface OwnerService {
    OwnerDto createOwner(OwnerDto ownerDto);
    List<OwnerDto> getAllOwners();
    OwnerDto getOwnerByID(int id);
    OwnerDto updateOwner(OwnerDto ownerDto, int id);
    void deleteOwnerByID(int id);
}
