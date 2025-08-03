package com.backend.Arch.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.Arch.DTO.UserLoginDto;
import com.backend.Arch.DTO.UserRegistrationDto;
import com.backend.Arch.Mapper.UserMapper;
import com.backend.Arch.Model.Users;
import com.backend.Arch.Reppo.UserReppo;

import jakarta.validation.Valid;

@Service
public class UserService {
	
	@Autowired
	UserReppo userReppo;
	
	@Autowired 
	JwtService jwtService;
	
	@Autowired
	AuthenticationManager am;
	

    @Autowired
    UserMapper userMapper;
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	

	public ResponseEntity<?> register(@Valid UserRegistrationDto userDto) {
		Users user=userReppo.findByUsername(userDto.username());
		  if (user != null) {
		        return ResponseEntity
		                .status(HttpStatus.CONFLICT)
		                .body("❌ Username already exists!");
		    }
		
	    Users newUser = userMapper.toUser(userDto);
	    newUser.setPassword(encoder.encode(userDto.password()));
	    
	    
	    userReppo.save(newUser);
	    
	    return ResponseEntity.ok("✅ User registered successfully");
	    
	    
	}

	
	
	public ResponseEntity<String> login(@Valid UserLoginDto loginDto) {
			
		    try {
		        Authentication authentication = am.authenticate(
		            new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password())
		        );

		        if (authentication.isAuthenticated()) {
		            String token = jwtService.generateToken(loginDto.username());
		            return ResponseEntity.ok(token);
		        }

		    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + e.getMessage()); // 🔥 Send to Postman
		    }

		    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
		}



}
