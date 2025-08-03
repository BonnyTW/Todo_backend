package com.backend.Arch.DTO;

public record AuthResponseDto(
    String token,
    String username,
    String role
) {}
