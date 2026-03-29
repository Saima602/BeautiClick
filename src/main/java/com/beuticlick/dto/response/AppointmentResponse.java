package com.beuticlick.dto.response;

import java.time.LocalDateTime;

import com.beuticlick.constant.StatusEnum;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AppointmentResponse {
    private Long id;
    private Long customerId;
    private Long serviceId;
    private Long staffId;
    private LocalDateTime appointmentTime;
    private Double servicePrice;
    private StatusEnum status;
}
