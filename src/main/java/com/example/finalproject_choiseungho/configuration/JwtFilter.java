package com.example.finalproject_choiseungho.configuration;

import com.example.finalproject_choiseungho.domain.entity.User;
import com.example.finalproject_choiseungho.domain.entity.UserRole;
import com.example.finalproject_choiseungho.service.UserService;
import com.example.finalproject_choiseungho.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Get token
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("HttpHeaders.AUTHORIZATION : {}", authorizationHeader);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.info("헤더가 null이거나 잘못된 token 사용, authorizationHeader : {}", authorizationHeader);
            filterChain.doFilter(request, response);
            return;
        }

        // Extract token
        String token;

        try {
            token = authorizationHeader.split(" ")[1];
            log.info("Extract token succeed, token : {}", token);
        } catch (Exception e) {
            log.info("Extract token failure, Exception e : {}", e);
            filterChain.doFilter(request, response);
            return;
        }

        // Validate token
        if (JwtUtil.isExpired(token, secretKey)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Get UserName from token
        final String userName = JwtUtil.getUserNameFromToken(token, secretKey);
        log.info("Get UserName from token succeed, userName : {}", userName);

        // Get UserRole from token
        User user;
        UserRole userRole;

        try {
            user = userService.getUserByUserName(userName);
            log.info("User found : {}", user.getUserName());
            userRole = user.getRole();
            log.info("User Role : {}", userRole);
        } catch (Exception e) {
            log.info("Invalid UserName or Invalid UserRole : {}", e);
            filterChain.doFilter(request, response);
            return;
        }

        // Enable authentication if token is valid
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                user.getUserName(),
                null,
                List.of(new SimpleGrantedAuthority(user.getRole().name())));
        log.info("Authentication enabled : {}", usernamePasswordAuthenticationToken);
        log.info("A granted authority textual role : {}", user.getRole().name());

        // Set authenticationtoken details
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        log.info("Authentication details : {}", usernamePasswordAuthenticationToken.getDetails().toString());

        // Set authentication for Security context
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        log.info("Security Context : {}", SecurityContextHolder.getContext().getAuthentication().toString());

        filterChain.doFilter(request, response);
    }
}
