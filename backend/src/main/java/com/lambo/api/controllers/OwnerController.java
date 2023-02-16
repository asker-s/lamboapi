package com.lambo.api.controllers;

import com.lambo.api.dto.OwnerDto;
import com.lambo.api.dto.OwnerResponse;
import com.lambo.api.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/")
@CrossOrigin("http://localhost:3000")
public class OwnerController {

    private OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    //works fine, frontend implemented
    @GetMapping("owner")
    public List<OwnerDto> getOwners(
    ) {
        return ownerService.getAllOwners();
    }

    //works fine, frontend implemented
    @GetMapping("owner/{id}")
    public ResponseEntity<OwnerDto> ownerDetail(@PathVariable int id) {
        return ResponseEntity.ok(ownerService.getOwnerByID(id));
    }

    //works fine, frontend implemented
    @PostMapping("owner/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OwnerDto> createOwner(@RequestBody OwnerDto ownerDto) {
        return new ResponseEntity<>(ownerService.createOwner(ownerDto), HttpStatus.CREATED);
    }

    //works fine, frontend implemented
    @PutMapping("owner/{id}/update")
    public ResponseEntity<OwnerDto> updateOwner(@RequestBody OwnerDto ownerDto, @PathVariable("id") int ownerId) {
        OwnerDto response = ownerService.updateOwner(ownerDto, ownerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //works fine, frontend implemented
    @DeleteMapping("owner/{id}/delete")
    public ResponseEntity<String> deleteOwner(@PathVariable("id") int ownerId) {
        ownerService.deleteOwnerByID(ownerId);
        return new ResponseEntity<>("Owner deleted", HttpStatus.OK);
    }
}
