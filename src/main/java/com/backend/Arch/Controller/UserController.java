package com.backend.Arch.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Arch.DTO.UserLoginDto;
import com.backend.Arch.DTO.UserRegistrationDto;
import com.backend.Arch.DTO.UserResponseDto;
import com.backend.Arch.Mapper.UserMapper;
import com.backend.Arch.Model.Users;
import com.backend.Arch.Reppo.UserReppo;
import com.backend.Arch.Service.JwtService;
import com.backend.Arch.Service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;



@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	UserReppo userReppo;
	
	@Autowired
	UserMapper userMapper;
	
	 	@PostMapping("/register")
	    public ResponseEntity<?> register(@RequestBody @Valid UserRegistrationDto userDto) {
	        return userService.register(userDto);
	    }

	    @PostMapping("/login")
	    public ResponseEntity<String> login(@RequestBody @Valid UserLoginDto loginDto) {
	        return userService.login(loginDto);
	    }
	    
	    @GetMapping("/users")
	    public ResponseEntity<?> getAllUsers(HttpServletRequest request) {
	        String token = request.getHeader("Authorization").substring(7);
	        String username = jwtService.extractUsername(token);
	        Users currentUser = userReppo.findByUsername(username);

	        if (currentUser == null || !"ADMIN".equals(currentUser.getRole())) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Only admin can view users");
	        }

	        // Fetch all users and filter out the "admin" user
	        List<Users> allUsers = userReppo.findAll();
	        List<UserResponseDto> filteredUsers = allUsers.stream()
	            .filter(user -> !"admin".equalsIgnoreCase(user.getUsername()))
	            .map(userMapper::toDto)  // map entity to DTO 
	            .toList();

	        return ResponseEntity.ok(filteredUsers);
	    }
}
