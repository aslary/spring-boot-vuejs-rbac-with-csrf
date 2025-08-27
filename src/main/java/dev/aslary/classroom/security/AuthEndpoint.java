package dev.aslary.classroom.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthEndpoint {

  private final AuthenticationManager authenticationManager;
  private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

  public AuthEndpoint(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @GetMapping("/me")
  public CurrentUserDto auth(Authentication authentication) {
    if (authentication.getPrincipal() instanceof User user) {
      return CurrentUserDto.builder()
        .username(user.getUsername())
        .authorities(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
        .build();
    }

    return null;
  }

  @PostMapping("/login")
  public void login(
    @RequestBody LoginRequestDto loginRequestDto,
    HttpServletRequest request,
    HttpServletResponse response
  ) {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(loginRequestDto.username(), loginRequestDto.password())
    );

    SecurityContext context = SecurityContextHolder.getContext();
    context.setAuthentication(authentication);

    securityContextRepository.saveContext(context, request, response);
  }

  @PostMapping("/logout")
  public void logout(HttpServletRequest request) {
    SecurityContextHolder.clearContext();
    request.getSession().invalidate();
  }
}
