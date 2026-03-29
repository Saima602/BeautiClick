package com.beuticlick.dto;

import com.beuticlick.dto.request.AppointmentRequest;
import com.beuticlick.dto.request.CustomerRequest;
import com.beuticlick.dto.request.SalonServiceRequest;
import com.beuticlick.dto.request.StaffRequest;
import com.beuticlick.dto.response.AppointmentResponse;
import com.beuticlick.dto.response.CustomerResponse;
import com.beuticlick.dto.response.SalonServiceResponse;
import com.beuticlick.dto.response.StaffResponse;
import com.beuticlick.entity.Appointment;
import com.beuticlick.entity.Customer;
import com.beuticlick.entity.SalonService;
import com.beuticlick.entity.Staff;

public class DtoMapper {

    // ── Customer ──────────────────────────────────────────────────────────────

    public static Customer toEntity(CustomerRequest req) {
        Customer c = new Customer();
        c.setName(req.getName());
        c.setPhone(req.getPhone());
        c.setEmail(req.getEmail());
        c.setCity(req.getCity());
        return c;
    }

    public static CustomerResponse toResponse(Customer c) {
        return CustomerResponse.builder()
            .id(c.getId())
            .name(c.getName())
            .phone(c.getPhone())
            .email(c.getEmail())
            .city(c.getCity())
            .build();
    }

    // ── Staff ─────────────────────────────────────────────────────────────────

    public static Staff toEntity(StaffRequest req) {
        Staff s = new Staff();
        s.setName(req.getName());
        s.setPhone(req.getPhone());
        s.setEmail(req.getEmail());
        s.setRole(req.getRole());
        s.setAvailable(req.getAvailable() != null ? req.getAvailable() : true);
        return s;
    }

    public static StaffResponse toResponse(Staff s) {
        return StaffResponse.builder()
            .id(s.getId())
            .name(s.getName())
            .phone(s.getPhone())
            .email(s.getEmail())
            .role(s.getRole())
            .available(s.getAvailable())
            .build();
    }

    // ── SalonService ──────────────────────────────────────────────────────────

    public static SalonService toEntity(SalonServiceRequest req) {
        SalonService sv = new SalonService();
        sv.setName(req.getName());
        sv.setCategory(req.getCategory());
        sv.setDescription(req.getDescription());
        sv.setPrice(req.getPrice());
        sv.setDurationMinutes(req.getDurationMinutes());
        return sv;
    }

    public static SalonServiceResponse toResponse(SalonService sv) {
        return SalonServiceResponse.builder()
            .id(sv.getId())
            .name(sv.getName())
            .category(sv.getCategory())
            .description(sv.getDescription())
            .price(sv.getPrice())
            .durationMinutes(sv.getDurationMinutes())
            .active(sv.getActive())
            .build();
    }

    // ── Appointment ───────────────────────────────────────────────────────────

    public static Appointment toEntity(AppointmentRequest req) {
        Appointment a = new Appointment();
        a.setCustomerId(req.getCustomerId());
        a.setServiceId(req.getServiceId());
        a.setStaffId(req.getStaffId());
        a.setAppointmentTime(req.getAppointmentTime());
        return a;
    }

    public static AppointmentResponse toResponse(Appointment a) {
        return AppointmentResponse.builder()
            .id(a.getId())
            .customerId(a.getCustomerId())
            .serviceId(a.getServiceId())
            .staffId(a.getStaffId())
            .appointmentTime(a.getAppointmentTime())
            .servicePrice(a.getServicePrice())
            .status(a.getStatus())
            .build();
    }

}
