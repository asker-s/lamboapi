package com.lambo.api.service;

import com.lambo.api.dto.GarageDto;

import java.util.List;

public interface GarageService {
    GarageDto createGarage(int ownerId, GarageDto garageDto);
    List<GarageDto> getGaragesByOwnerId(int id);
    GarageDto getGarageById(int garageId, int ownerId);
    void deleteGarage(int ownerId, int garageId);
    GarageDto assignCarToGarage(int ownerId, int garageId, int carId);
    List<GarageDto> getGaragesCarHasAccessTo(int carId);
}