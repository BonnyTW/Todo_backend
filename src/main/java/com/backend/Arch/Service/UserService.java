package com.backend.Arch.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.Arch.DTO.ForgotPasswordRequestDto;
import com.backend.Arch.DTO.ResetPasswordDto;
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
	MailService mailService;
	

    @Autowired
    UserMapper userMapper;
    
    @Autowired
    private JavaMailSender mailSender;

	
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
	    
	    mailService.sendRegistrationEmail(userDto.email(), userDto.username());

        return ResponseEntity.ok("User registered and email sent!");
	    
	    
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
		        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + e.getMessage()); 
		    }

		    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
		}



	public ResponseEntity<String> handleForgotPassword(ForgotPasswordRequestDto requestDto) {
	    Users user = userReppo.findByEmail(requestDto.email());
	    if (user == null) {
	        return ResponseEntity.ok("✅ If the account exists, a reset link has been sent");
	    }

	    
	    String token = jwtService.generateToken(user.getUsername()); 
	    String resetLink = "http://localhost:3000/reset-password?token=" + token;

	    // Send email
	    SimpleMailMessage message = new SimpleMailMessage();
	    message.setTo(user.getEmail());
	    message.setSubject("🔐 Reset Your Password");
	    message.setText("Click the link to reset your password:\n" + resetLink + 
	        "\n\nThis link expires in 10 hr.");

	    mailSender.send(message);
	    return ResponseEntity.ok("✅ If the account exists, a reset link has been sent");
	}



	public ResponseEntity<String> handleResetPassword(ResetPasswordDto resetDto) {
	    try {
	        String token = resetDto.token();
	        String username = jwtService.extractUsername(token);

	        Users user = userReppo.findByUsername(username);
	        if (user == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ User not found");
	        }

	       
	        UserDetails userDetails = userMapper.toUserDetails(user);


	        if (!jwtService.validateToken(token, userDetails)) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Token is invalid or expired");
	        }

	        
	        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	        user.setPassword(encoder.encode(resetDto.newPassword()));
	        userReppo.save(user);

	        return ResponseEntity.ok("✅ Password reset successful");

	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("❌ Invalid or expired token");
	    }
	}

}
