package com.beuticlick.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StaffResponse {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String role;
    private Boolean available;
}
