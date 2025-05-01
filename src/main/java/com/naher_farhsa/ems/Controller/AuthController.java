package com.naher_farhsa.ems.Controller;

import com.naher_farhsa.ems.DTO.JwtResponse;
import com.naher_farhsa.ems.DTO.LoginDTO;
import com.naher_farhsa.ems.Entity.User;

import com.naher_farhsa.ems.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/ems/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        return ResponseEntity.ok(authService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(authService.login(loginDTO));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(authService.logout(authHeader));
    }
}
