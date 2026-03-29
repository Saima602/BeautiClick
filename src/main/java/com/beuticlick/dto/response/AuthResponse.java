package com.beuticlick.dto.response;

import com.beuticlick.constant.Role;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {
    private String token;
    private String name;
    private String email;
    private Role role;
    private Long salonId;
}
