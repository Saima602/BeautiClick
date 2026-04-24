package com.beuticlick.service;

import java.util.Random;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.beuticlick.constant.Role;
import com.beuticlick.dto.request.LoginRequest;
import com.beuticlick.dto.request.RegisterRequest;
import com.beuticlick.dto.response.AuthResponse;
import com.beuticlick.entity.Salon;
import com.beuticlick.entity.User;
import com.beuticlick.exception.BusinessException;
import com.beuticlick.repository.SalonRepository;
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
	private final SalonRepository salonRepository;

	public AuthResponse register(RegisterRequest request) {
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new BusinessException("Email already registered: " + request.getEmail());
		}

		if (request.getRole() != Role.SALON_ADMIN) {
			throw new BusinessException("Only salon admin can create salon");
		}
		String salonCode = generateSalonCode(request.getSalon().getName());
		Salon salon = Salon.builder().name(request.getSalon().getName()).address(request.getSalon().getAddress())
				.phone(request.getSalon().getPhone()).salonCode(salonCode).build();

		Salon savedSalon = salonRepository.save(salon);

		User user = User.builder().name(request.getName()).email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword())).role(request.getRole()).salon(savedSalon)
				.build();

		User userSave = userRepository.save(user);

		return AuthResponse.builder().token(jwtService.generateToken(userSave)).name(userSave.getName())
				.email(userSave.getEmail()).role(userSave.getRole()).salonCode(salonCode).build();
	}

	public AuthResponse login(LoginRequest request) {
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		} catch (Exception ex) {
			throw new BusinessException("Authentication Failed !!!");
		}
		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new BusinessException("User not found"));

		return AuthResponse.builder().token(jwtService.generateToken(user)).name(user.getName()).email(user.getEmail())
				.role(user.getRole()).salonCode(user.getSalon() != null ? user.getSalon().getSalonCode() : null)
				.build();

	}

	public String generateSalonCode(String salonName) {
		String prefix = salonName.replaceAll("[^A-Za-z]", "").toUpperCase().substring(0,
				Math.min(4, salonName.length()));

		int random = new Random().nextInt(9000) + 1000;

		return prefix + random; // e.g. TABC4821
	}
}
