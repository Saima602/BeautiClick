package com.beuticlick.dto;

import com.beuticlick.entity.Appointment;
import com.beuticlick.dto.response.AppointmentResponse;

public class AppointmentMapper {

    public static AppointmentResponse toResponse(Appointment entity) {
        return AppointmentResponse.builder()
                .id(entity.getId())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .status(entity.getStatus().name())
                .staffName(entity.getStaff().getName())
                .serviceName(entity.getService().getName())
                .customerName(entity.getCustomer().getName())
                .notes(entity.getNotes())
                .build();
    }
}