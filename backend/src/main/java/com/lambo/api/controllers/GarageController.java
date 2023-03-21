package com.lambo.api.controllers;

import com.lambo.api.dto.GarageDto;
import com.lambo.api.service.GarageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Garage Controller")
@RestController
@RequestMapping("/api/")
@CrossOrigin("http://localhost:3000")
public class    GarageController {

    private final GarageService garageService;

    @Autowired
    public GarageController(GarageService garageService) {
        this.garageService = garageService;
    }

    //works fine, frontend implemented
    @Operation(operationId = "createGarage",
            summary = "Creates a new car and save it in the database",

            parameters = {
                    @Parameter(name = "ownerId", schema = @Schema(implementation = Integer.class), description = "owner id"),
                    @Parameter(name = "garageId", schema = @Schema(implementation = Integer.class), description = "garage id"),
                    @Parameter(name = "cardId", schema = @Schema(implementation = Integer.class), description = "car id")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = GarageDto.class))
            )
    )
    @ApiResponse(responseCode = "201", description = "The garage is created",
            content = @Content(schema = @Schema(implementation = GarageDto.class))
    )
    @PostMapping("owner/{ownerId}/garage")
    public ResponseEntity<GarageDto> createGarage(@PathVariable(value = "ownerId") int ownerId, @RequestBody GarageDto garageDto) {
        return new ResponseEntity<>(garageService.createGarage(ownerId, garageDto), HttpStatus.CREATED);
    }

    @Operation(operationId = "getGaragesByOwnerId",
            summary = "Retrieves the garages by owner id",
            parameters = @Parameter(name = "ownerId", schema = @Schema(implementation = Integer.class), description = "owner id")
    )
    //works fine, frontend implemented
    @GetMapping("owner/{ownerId}/garage")
    public List<GarageDto> getGaragesByOwnerId(@PathVariable(value = "ownerId") int ownerId) {
        return garageService.getGaragesByOwnerId(ownerId);
    }

    @Operation(operationId = "assignCarToGarage",
            summary = "Assigns car to garage",
            parameters = {
                    @Parameter(name = "ownerId", schema = @Schema(implementation = Integer.class), description = "owner id"),
                    @Parameter(name = "garageId", schema = @Schema(implementation = Integer.class), description = "garage id"),
                    @Parameter(name = "carId", schema = @Schema(implementation = Integer.class), description = "car id"),
            }
    )
    //works fine, frontend implemented
    @PutMapping("owner/{ownerId}/garages/{garageId}/cars/{carId}")
    public ResponseEntity<GarageDto> assignCarToGarage(@PathVariable(value = "ownerId") int ownerId,
                                                       @PathVariable(value = "garageId") int garageId,
                                                       @PathVariable(value = "carId") int carId) {
        return new ResponseEntity<>(garageService.assignCarToGarage(ownerId, garageId, carId), HttpStatus.OK);
    }


    @Operation(operationId = "getGarageById",
            summary = "Retrieves garage by id",
            parameters = {
                    @Parameter(name = "ownerId", schema = @Schema(implementation = Integer.class), description = "owner id"),
                    @Parameter(name = "garageId", schema = @Schema(implementation = Integer.class), description = "garage id"),
            }
    )
    //works fine, frontend implemented
    @GetMapping("owner/{ownerId}/garages/{garageId}")
    public ResponseEntity<GarageDto> getGarageById(@PathVariable(value = "ownerId") int ownerId, @PathVariable(value = "garageId") int garageId) {
        GarageDto garageDto = garageService.getGarageById(garageId, ownerId);
        return new ResponseEntity<>(garageDto, HttpStatus.OK);
    }

    @Operation(operationId = "deleteGarage",
            summary = "Deletes garage by its id and owner id",
            parameters = {
                    @Parameter(name = "ownerId", schema = @Schema(implementation = Integer.class), description = "owner id"),
                    @Parameter(name = "garageId", schema = @Schema(implementation = Integer.class), description = "garage id"),
            }
    )
    //works fine, frontend implemented
    @DeleteMapping("owner/{ownerId}/garages/{garageId}")
    public ResponseEntity<String> deleteGarage(@PathVariable(value = "ownerId") int ownerId, @PathVariable(value = "garageId") int garageId) {
        garageService.deleteGarage(ownerId, garageId);
        return new ResponseEntity<>("Garage deleted successfully", HttpStatus.OK);
    }

    @Operation(operationId = "getGaragesCarHasAccessTo",
            summary = "Gets garages car has access to by car id",
            parameters = @Parameter(name = "garageId", schema = @Schema(implementation = Integer.class), description = "garage id"))
    //works fine, frontend implemented
    @GetMapping("cars/{carId}/garages")
    public List<GarageDto> getGaragesCarHasAccessTo(@PathVariable(value = "carId") int carId) {
        return garageService.getGaragesCarHasAccessTo(carId);
    }


}
