package com.backend.Arch.DTO;

public record UserResponseDto(
	    int id,
	    String username,
	    String email,
	    String role
	) {}