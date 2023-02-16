package com.lambo.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Garage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
                name = "garage_car",
                joinColumns = @JoinColumn(name = "garage_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "car_id", referencedColumnName = "id")
    )
    private Set<Car> cars = new HashSet<>();

    public void addCar(Car car) {
        this.cars.add(car);
        car.getGarages().add(this);
    }

    public void removeCar(Car car) {
        this.cars.remove(car);
        car.getGarages().remove(this);
    }

}
