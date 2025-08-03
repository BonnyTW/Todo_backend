package com.backend.Arch.DTO;

public record UpdatePasswordDto(
	    String oldPassword,
	    String newPassword
	) {}