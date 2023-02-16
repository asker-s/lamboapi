package com.lambo.api.dto;

import com.lambo.api.models.Car;
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
public class GarageDto {
    private int id;
    private String name;
    private String location;
    private Set<CarDto> cars = new HashSet<>();
}
