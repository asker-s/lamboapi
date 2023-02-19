package com.lambo.api.controllers;

import com.lambo.api.dto.CarDto;
import com.lambo.api.dto.OwnerDto;
import com.lambo.api.service.OwnerService;
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


@Tag(name = "Owner Controller")
@RestController
@RequestMapping("/api/owner")
@CrossOrigin("http://localhost:3000")
public class OwnerController {

    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    //works fine, frontend implemented
    @Operation(operationId = "getOwners", summary = "Retrieves all owners")
    @GetMapping("")
    public List<OwnerDto> getOwners() {
        return ownerService.getAllOwners();
    }

    //works fine, frontend implemented
    @GetMapping("/{ownerId}")
    @Operation(operationId = "ownerDetail",
            summary = "Retrieves all the details about an owner by id",
            parameters = @Parameter(name = "ownerId", schema = @Schema(implementation = Integer.class), description = "owner id"))
    public ResponseEntity<OwnerDto> ownerDetail(@PathVariable int ownerId) {
        return ResponseEntity.ok(ownerService.getOwnerByID(ownerId));
    }

    //works fine, frontend implemented
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "createOwner",
            summary = "Creates a new owner")
    @ApiResponse(responseCode = "201", description = "The car is owner",
            content = @Content(schema = @Schema(implementation = OwnerDto.class))
    )
    public ResponseEntity<OwnerDto> createOwner(@RequestBody OwnerDto ownerDto) {
        return new ResponseEntity<>(ownerService.createOwner(ownerDto), HttpStatus.CREATED);
    }

    //works fine, frontend implemented
    @Operation(operationId = "updateOwner",
            summary = "Updates owner",
            parameters = @Parameter(name = "id", schema = @Schema(implementation = Integer.class)),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = OwnerDto.class))
            )
    )
    @PutMapping("/{id}")
    public ResponseEntity<OwnerDto> updateOwner(@RequestBody OwnerDto ownerDto, @PathVariable("id") int ownerId) {
        OwnerDto response = ownerService.updateOwner(ownerDto, ownerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(operationId = "deleteOwner",
            summary = "Deletes owner by id",
            parameters = @Parameter(name = "id", schema = @Schema(implementation = Integer.class)))
    //works fine, frontend implemented
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOwner(@PathVariable("id") int ownerId) {
        ownerService.deleteOwnerByID(ownerId);
        return new ResponseEntity<>("Owner deleted", HttpStatus.OK);
    }
}