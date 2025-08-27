package dev.aslary.classroom.security;

import java.util.Set;
import lombok.Builder;

@Builder
public record CurrentUserDto(String username, Set<String> authorities) {}
