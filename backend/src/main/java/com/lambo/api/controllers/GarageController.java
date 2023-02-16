package com.lambo.api.controllers;

import com.lambo.api.dto.GarageDto;
import com.lambo.api.service.GarageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@CrossOrigin("http://localhost:3000")
public class GarageController {

    private GarageService garageService;

    @Autowired
    public GarageController(GarageService garageService) { this.garageService = garageService; }

    //works fine, frontend implemented
    @PostMapping("owner/{ownerId}/garage")
    public ResponseEntity<GarageDto> createGarage(@PathVariable(value = "ownerId") int ownerId, @RequestBody GarageDto garageDto) {
        return new ResponseEntity<>(garageService.createGarage(ownerId, garageDto), HttpStatus.CREATED);
    }

    //works fine, frontend implemented
    @GetMapping("owner/{ownerId}/garages")
    public List<GarageDto> getGaragesByOwnerId(@PathVariable(value = "ownerId") int ownerId) {
        return garageService.getGaragesByOwnerId(ownerId);
    }

    //works fine, frontend implemented
    @PutMapping("owner/{ownerId}/garages/{garageId}/cars/{carId}/assign")
    public ResponseEntity<GarageDto> assignCarToGarage(@PathVariable(value = "ownerId") int ownerId,
                                                    @PathVariable(value = "garageId") int garageId,
                                                    @PathVariable(value = "carId") int carId) {
        return new ResponseEntity<>(garageService.assignCarToGarage(ownerId, garageId, carId), HttpStatus.OK);
    }

    //works fine, frontend implemented
    @GetMapping("owner/{ownerId}/garages/{id}")
    public ResponseEntity<GarageDto> getGarageById(@PathVariable(value = "ownerId") int ownerId, @PathVariable(value = "id") int garageId) {
        GarageDto garageDto = garageService.getGarageById(garageId, ownerId);
        return new ResponseEntity<>(garageDto, HttpStatus.OK);
    }

    //works fine, frontend implemented
    @DeleteMapping("owner/{ownerId}/garages/{id}")
    public ResponseEntity<String> deleteGarage(@PathVariable(value = "ownerId") int ownerId, @PathVariable(value = "id") int garageId) {
        garageService.deleteGarage(ownerId, garageId);
        return new ResponseEntity<>("Garage deleted successfully", HttpStatus.OK);
    }

    //works fine, frontend implemented
    @GetMapping("cars/{carId}/garages")
    public List<GarageDto> getGaragesCarHasAccessTo(@PathVariable(value = "carId") int carId) {
        return garageService.getGaragesCarHasAccessTo(carId);
    }


}
