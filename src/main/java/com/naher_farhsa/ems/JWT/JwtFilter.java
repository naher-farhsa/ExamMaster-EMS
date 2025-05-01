package com.naher_farhsa.ems.JWT;


import com.naher_farhsa.ems.Service.CustomUserDetailService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailService userDetailsService;
    @Autowired
    private  JwtUtil jwtUtil;
    @Autowired
    private JwtTokenStore jwtTokenStore;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Get the Authorization header from the request
        String authorizationHeader = request.getHeader("Authorization");
        log.info("Auth Header: {}", authorizationHeader);

        String userName = null;
        String jwt = null;

        // Check if the header contains a Bearer token
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // Extract token from header
            userName = jwtUtil.extractUserName(jwt); // Extract email from the token
        }

        // Validate token and set user details in the security context
        if (userName != null) {
            log.info("Email extracted from token: {}", userName);
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
            if (jwtUtil.validateToken(jwt) && jwtTokenStore.isValidToken(userName, jwt)) {
                log.info("Token is valid");
                // Create authentication token and set it in the security context
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            else {
                log.info("Token validation failed");
            }
        }
        // Continue with the request
        chain.doFilter(request, response);
    }
}
