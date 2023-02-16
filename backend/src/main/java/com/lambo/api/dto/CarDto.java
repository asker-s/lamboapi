package com.lambo.api.dto;

import com.lambo.api.models.Garage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {
    private int id;
    private String brand;
    private String model;
    private Set<GarageDto> garageSet = new HashSet<>();
}
