package com.backend.Arch.Mapper;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.backend.Arch.DTO.UserRegistrationDto;
import com.backend.Arch.DTO.UserResponseDto;
import com.backend.Arch.Model.Users;

@Component
public class UserMapper {

    public Users toUser(UserRegistrationDto dto) {
        Users user = new Users();
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(dto.password()); // encode later in service
        user.setRole("USER"); 
        return user;
    }

    public UserResponseDto toDto(Users user) {
        return new UserResponseDto(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRole()
        );
    }

    public UserDetails toUserDetails(Users user) {
        return User
            .withUsername(user.getUsername())
            .password(user.getPassword())
            .authorities(user.getRole())
            .build();
    }
}
