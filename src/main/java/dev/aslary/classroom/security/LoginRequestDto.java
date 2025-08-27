package dev.aslary.classroom.security;

import lombok.Builder;

@Builder
public record LoginRequestDto(String username, String password) {}
