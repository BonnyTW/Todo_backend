package com.backend.Arch.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Arch.DTO.UserLoginDto;
import com.backend.Arch.DTO.UserRegistrationDto;
import com.backend.Arch.Service.UserService;

import jakarta.validation.Valid;



@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	 	@PostMapping("/register")
	    public ResponseEntity<?> register(@RequestBody @Valid UserRegistrationDto userDto) {
	        return userService.register(userDto);
	    }

	    @PostMapping("/login")
	    public ResponseEntity<String> login(@RequestBody @Valid UserLoginDto loginDto) {
	        return userService.login(loginDto);
	    }
}
