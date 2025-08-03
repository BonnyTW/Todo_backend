package com.backend.Arch.Mapper;


import org.springframework.stereotype.Component;
import com.backend.Arch.DTO.UserRegistrationDto;
import com.backend.Arch.Model.Users;

@Component
public class UserMapper {

    public Users toUser(UserRegistrationDto dto) {
        Users user = new Users();
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(dto.password()); // encode later in service
        user.setRole("USER"); // default role
        return user;
    }
}
