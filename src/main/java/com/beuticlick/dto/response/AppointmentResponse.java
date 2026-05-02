package com.beuticlick.dto.response;

import java.time.LocalDateTime;

import com.beuticlick.constant.StatusEnum;
import com.beuticlick.entity.Customer;
import com.beuticlick.entity.SalonService;
import com.beuticlick.entity.Staff;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AppointmentResponse {
	 private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String status;

    private String staffName;
    private String serviceName;
    private String customerName;

    private String notes;
}
