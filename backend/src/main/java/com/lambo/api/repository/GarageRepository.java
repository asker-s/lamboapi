package com.lambo.api.repository;

import com.lambo.api.models.Garage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GarageRepository extends JpaRepository<Garage, Integer> {
    List<Garage> findByOwnerId(int ownerId);
}