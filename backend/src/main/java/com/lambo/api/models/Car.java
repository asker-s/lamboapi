package com.lambo.api.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@EqualsAndHashCode(exclude="garages")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String brand;
    private String model;

    @ManyToMany(mappedBy = "cars", fetch = FetchType.LAZY)
    private Set<Garage> garages = new HashSet<>();

    public void addGarage(Garage garage) {
        this.garages.add(garage);
        garage.getCars().add(this);
    }

    public void removeGarage(Garage garage) {
        this.garages.remove(garage);
        garage.getCars().remove(this);
    }

}
