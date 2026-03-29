package com.beuticlick.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerResponse {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String city;
}
