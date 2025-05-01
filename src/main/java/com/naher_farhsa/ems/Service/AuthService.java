package com.naher_farhsa.ems.Service;

import com.naher_farhsa.ems.JWT.JwtTokenStore;
import com.naher_farhsa.ems.JWT.JwtUtil;
import com.naher_farhsa.ems.DTO.JwtResponse;
import com.naher_farhsa.ems.DTO.LoginDTO;
import com.naher_farhsa.ems.Entity.User;
import com.naher_farhsa.ems.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private JwtTokenStore jwtTokenStore;

    public String register(User user) {
        if (userRepository.findByUserName(user.getUserName()) != null) {
            return "User already registered!";
        }
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        userRepository.save(user);
        return "User registered successfully!";
    }

    public JwtResponse login(LoginDTO loginDTO) {
        // Check if already logged in
        if (jwtTokenStore.isTokenPresent(loginDTO.getUserName())) {
            String existingToken = jwtTokenStore.getToken(loginDTO.getUserName());
            if (!jwtUtil.isTokenExpired(existingToken)) {
                throw new RuntimeException("User already logged in. Please logout first.");
            } else {
                jwtTokenStore.removeToken(loginDTO.getUserName(),existingToken); // clean expired token
            }
        }

        // Authenticate
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUserName(), loginDTO.getUserPassword()
                )
        );

        String token = jwtUtil.generateToken(loginDTO.getUserName());
        jwtTokenStore.storeToken(loginDTO.getUserName(), token);
        User user = userRepository.findByUserName(loginDTO.getUserName());

        return new JwtResponse(token, user.getUserRole());
    }

    public String logout(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String userName = jwtUtil.extractUserName(token);
            boolean removed = jwtTokenStore.removeToken(userName, token);
            if (removed)
                return "Logged out successfully";
            else
                return "Invalid or already expired token";
        }
            return "No token found to logout";
    }
}

