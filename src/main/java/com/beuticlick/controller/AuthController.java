package com.beuticlick.controller;

import org.springframework.web.bind.annotation.*;

import com.beuticlick.dto.request.LoginRequest;
import com.beuticlick.dto.request.RegisterRequest;
import com.beuticlick.dto.response.AuthResponse;
import com.beuticlick.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    // POST /auth/register — create a new user account
    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return service.register(request);
    }

    // POST /auth/login — get a JWT token
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return service.login(request);
    }
}
