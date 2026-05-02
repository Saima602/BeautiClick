package com.beuticlick.dto;

import com.beuticlick.constant.ServiceCategoryEnum;
import com.beuticlick.constant.StaffRole;
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

	public static CustomerResponse toResponse(Customer entity) {
		if (entity == null)
			return null;

		return CustomerResponse.builder().id(entity.getId()).name(entity.getName()).phone(entity.getPhone())
				.email(entity.getEmail()).build();
	}

	public static Customer toEntity(CustomerRequest dto) {
		if (dto == null)
			return null;

		Customer customer = new Customer();
		customer.setName(dto.getName());
		customer.setPhone(dto.getPhone());
		customer.setEmail(dto.getEmail());

		return customer;
	}

	// ── Staff ─────────────────────────────────────────────────────────────────

	public static Staff toEntity(StaffRequest req) {
		Staff s = new Staff();
		s.setName(req.getName());
		s.setPhone(req.getPhone());
		s.setEmail(req.getEmail());
		s.setRole(StaffRole.valueOf(req.getRole()));
		s.setSpecialization(req.getSpecialization());
		s.setExperienceYears(req.getExperienceYears());
		s.setAvailable(req.getAvailable() != null ? req.getAvailable() : true);
		return s;
	}

	public static StaffResponse toResponse(Staff s) {
		return StaffResponse.builder().id(s.getId()).name(s.getName()).phone(s.getPhone()).email(s.getEmail())
				.role(StaffRole.valueOf(s.getRole().name())).specialization(s.getSpecialization())
				.experienceYears(s.getExperienceYears()).available(s.getAvailable()).build();
	}

	// ── SalonService ──────────────────────────────────────────────────────────

	public static SalonService toEntity(SalonServiceRequest req) {
		SalonService sv = new SalonService();
		sv.setName(req.getName());
		sv.setCategory(ServiceCategoryEnum.valueOf(req.getCategory()));
		sv.setDescription(req.getDescription());
		sv.setPrice(req.getPrice());
		sv.setDurationMinutes(req.getDurationMinutes());
		return sv;
	}

	public static SalonServiceResponse toResponse(SalonService sv) {
		return SalonServiceResponse.builder().id(sv.getId()).name(sv.getName()).category(sv.getCategory().name())
				.description(sv.getDescription()).price(sv.getPrice()).durationMinutes(sv.getDurationMinutes())
				.active(sv.getActive()).build();
	}

	// ── Appointment ───────────────────────────────────────────────────────────

	public static Appointment toEntity(AppointmentRequest req) {
		Appointment a = new Appointment();
		a.setStartTime(req.getStartTime());
		return a;
	}

	
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
