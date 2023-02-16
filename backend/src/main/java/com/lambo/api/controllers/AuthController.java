package com.lambo.api.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
@CrossOrigin("http://localhost:3000")
public class AuthController {

	@PostMapping
	public Map<String,String> login(@RequestBody Map<String,String> obj) {
		String username = obj.get("username");
		String password = obj.get("password");
		Map<String,String> response = new HashMap<String,String>();
		if(password.equals("password")) {
			String secretKey = "secret";
			response.put("token", secretKey);
		}
		return response;
	}
}
