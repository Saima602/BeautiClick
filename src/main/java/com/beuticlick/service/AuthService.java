package com.beuticlick.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.beuticlick.dto.request.LoginRequest;
import com.beuticlick.dto.request.RegisterRequest;
import com.beuticlick.dto.response.AuthResponse;
import com.beuticlick.entity.User;
import com.beuticlick.exception.BusinessException;
import com.beuticlick.repository.UserRepository;
import com.beuticlick.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email already registered: " + request.getEmail());
        }

        User user = User.builder()
            .name(request.getName())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(request.getRole())
            .salonId(request.getSalonId())
            .build();

        userRepository.save(user);

        return AuthResponse.builder()
            .token(jwtService.generateToken(user))
            .name(user.getName())
            .email(user.getEmail())
            .role(user.getRole())
            .salonId(user.getSalonId())
            .build();
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new BusinessException("Invalid credentials"));

        return AuthResponse.builder()
            .token(jwtService.generateToken(user))
            .name(user.getName())
            .email(user.getEmail())
            .role(user.getRole())
            .salonId(user.getSalonId())
            .build();
    }
}
