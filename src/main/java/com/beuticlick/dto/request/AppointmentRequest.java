package com.beuticlick.dto.request;

import java.time.LocalDateTime;

import com.beuticlick.entity.Customer;
import com.beuticlick.entity.SalonService;
import com.beuticlick.entity.Staff;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentRequest {

	 @NotNull
    private Long staffId;

    @NotNull
    private Long serviceId;

    @NotNull
    private Long customerId;

    @NotNull
    private LocalDateTime startTime;

    private String notes;

}
