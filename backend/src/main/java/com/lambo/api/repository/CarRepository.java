package com.lambo.api.repository;

import com.lambo.api.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Integer> {
}