package com.beuticlick.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SalonServiceResponse {
    private Long id;
    private String name;
    private String category;
    private String description;
    private Double price;
    private Integer durationMinutes;
    private Boolean active;
}
