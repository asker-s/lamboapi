package com.lambo.api.controllers;

import java.util.HashMap;
import java.util.Map;

import com.lambo.api.dto.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth Controller")
@RestController
@RequestMapping("/api/login")
@CrossOrigin("http://localhost:3000")
public class AuthController {


    @Operation(operationId = "login")
    @PostMapping
    public Map<String, String> login(@RequestBody LoginRequest loginRequest) {

        Map<String, String> response = new HashMap<>();
        if (loginRequest.getPassword().equals("password")) {
            String secretKey = "secret";
            response.put("token", secretKey);
        }
        return response;
    }
}
