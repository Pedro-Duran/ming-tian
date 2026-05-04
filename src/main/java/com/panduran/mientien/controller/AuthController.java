package com.panduran.mientien.controller;

import com.panduran.mientien.dto.AuthDTO;
import com.panduran.mientien.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthDTO.Response> register(@RequestBody AuthDTO.Request request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDTO.Response> login(@RequestBody AuthDTO.Request request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
