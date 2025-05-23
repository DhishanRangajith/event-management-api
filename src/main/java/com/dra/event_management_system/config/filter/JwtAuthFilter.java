package com.dra.event_management_system.config.filter;

import java.io.IOException;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.dra.event_management_system.dto.UserData;
import com.dra.event_management_system.service.UserService;
import com.dra.event_management_system.util.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter{

    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);
        try{
            if(jwtService.validateToken(token) && SecurityContextHolder.getContext().getAuthentication() == null){
                String userEmail = this.jwtService.getUserEmailFromJWT(token);
                UserData userData = this.userService.getUserByEmail(userEmail);
                String roleName = "ROLE_" + userData.getRole().name();
                List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(roleName));
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userData, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            filterChain.doFilter(request, response);

        }catch(AuthenticationException ex){
            SecurityContextHolder.clearContext();
            authenticationEntryPoint.commence(request, response, ex);
        }
        
    }

}
