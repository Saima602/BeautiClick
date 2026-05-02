package com.beuticlick.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.beuticlick.constant.StatusEnum;
import com.beuticlick.dto.request.AppointmentRequest;
import com.beuticlick.entity.Appointment;
import com.beuticlick.entity.Customer;
import com.beuticlick.entity.Salon;
import com.beuticlick.entity.SalonService;
import com.beuticlick.entity.Staff;
import com.beuticlick.exception.BusinessException;
import com.beuticlick.repository.AppointmentRepository;
import com.beuticlick.repository.CustomerRepository;
import com.beuticlick.repository.SalonRepository;
import com.beuticlick.repository.SalonServiceRepository;
import com.beuticlick.repository.StaffRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository repository;
    private final StaffRepository staffRepository;
    private final SalonServiceRepository salonServiceRepository;
    private final CustomerRepository customerRepository;
    private final SalonRepository salonRepository;

    public Appointment book(AppointmentRequest request, Long salonId) {

        // 1️⃣ Fetch entities
        Staff staff = staffRepository.findById(request.getStaffId())
                .orElseThrow(() -> new BusinessException("Staff not found"));

        SalonService salonService = salonServiceRepository.findById(request.getServiceId())
                .orElseThrow(() -> new BusinessException("Service not found"));

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new BusinessException("Customer not found"));

        Salon salon = salonRepository.findById(salonId)
                .orElseThrow(() -> new BusinessException("Salon not found"));

        // 2️⃣ Multi-tenancy validation
        if (!staff.getSalonId().equals(salonId)) {
            throw new BusinessException("Staff does not belong to this salon");
        }

        // 3️⃣ Staff availability check
        if (!staff.getAvailable()) {
            throw new BusinessException("Staff is not available");
        }

        // 4️⃣ Time calculation
        LocalDateTime start = request.getStartTime();
        LocalDateTime end = start.plusMinutes(salonService.getDurationMinutes());

        // 5️⃣ Overlapping check
        boolean conflict = repository.existsOverlappingAppointment(
                staff.getId(),
                start,
                end
        );

        if (conflict) {
            throw new BusinessException("Staff already booked for this time slot");
        }

        // 6️⃣ Create appointment
        Appointment appointment = new Appointment();
        appointment.setStartTime(start);
        appointment.setEndTime(end);
        appointment.setStatus(StatusEnum.BOOKED);
        appointment.setNotes(request.getNotes());

        appointment.setStaff(staff);
        appointment.setService(salonService);
        appointment.setCustomer(customer);
        appointment.setSalon(salon);

        return repository.save(appointment);
    }

    public List<Appointment> getAll(Long salonId) {
        return repository.findBySalonId(salonId);
    }

    public Appointment getById(Long id, Long salonId) {
        Appointment appointment = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Appointment not found: " + id));
        if (!appointment.getSalon().getId().equals(salonId)) {
            throw new RuntimeException("Appointment " + id + " does not belong to salon " + salonId);
        }
        return appointment;
    }

    public List<Appointment> getByCustomer(Long customerId, Long salonId) {
        return repository.findBySalonIdAndCustomerId(salonId, customerId);
    }

    public List<Appointment> getByDate(LocalDate date, Long salonId) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59, 999999999);
        return repository.findBySalonIdAndStartTimeBetween(salonId, startOfDay, endOfDay);
    }

    public Appointment complete(Long id, Long salonId) {
        Appointment appointment = getById(id, salonId);
        appointment.setStatus(StatusEnum.COMPLETED);
        return repository.save(appointment);
    }

    public Appointment cancel(Long id, Long salonId) {
        Appointment appointment = getById(id, salonId);
        appointment.setStatus(StatusEnum.CANCELLED);
        return repository.save(appointment);
    }

}
