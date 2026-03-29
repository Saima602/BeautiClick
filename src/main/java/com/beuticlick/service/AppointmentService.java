package com.beuticlick.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.beuticlick.constant.StatusEnum;
import com.beuticlick.entity.Appointment;
import com.beuticlick.entity.BillingEntity;
import com.beuticlick.entity.SalonService;
import com.beuticlick.repository.AppointmentRepo;
import com.beuticlick.repository.BillingRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepo repo;
    private final InventoryService inventoryService;
    private final BillingRepo billingRepo;
    private final SalonServiceService salonServiceService;

    public Appointment book(Appointment appointment, Long salonId) {
        appointment.setSalonId(salonId);
        appointment.setStatus(StatusEnum.BOOKED);

        // Snapshot the price at booking time — billing stays accurate even if price changes later
        SalonService svc = salonServiceService.getById(appointment.getServiceId(), salonId);
        appointment.setServicePrice(svc.getPrice());

        return repo.save(appointment);
    }

    public List<Appointment> getAll(Long salonId) {
        return repo.findBySalonId(salonId);
    }

    public Appointment getById(Long id, Long salonId) {
        Appointment appt = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Appointment not found: " + id));
        if (!appt.getSalonId().equals(salonId)) {
            throw new RuntimeException("Appointment " + id + " does not belong to salon " + salonId);
        }
        return appt;
    }

    public List<Appointment> getByCustomer(Long customerId, Long salonId) {
        return repo.findBySalonIdAndCustomerId(salonId, customerId);
    }

    public Appointment cancel(Long appointmentId, Long salonId) {
        Appointment appt = getById(appointmentId, salonId);
        if (appt.getStatus() == StatusEnum.COMPLETED) {
            throw new RuntimeException("Cannot cancel a completed appointment.");
        }
        appt.setStatus(StatusEnum.CANCELLED);
        return repo.save(appt);
    }

    public void completeAppointment(Long appointmentId, Long salonId) {
        Appointment appt = getById(appointmentId, salonId);
        if (appt.getStatus() == StatusEnum.CANCELLED) {
            throw new RuntimeException("Cannot complete a cancelled appointment.");
        }

        appt.setStatus(StatusEnum.COMPLETED);
        repo.save(appt);

        // Reduce stock automatically
        inventoryService.useStock(1L, 10, appt.getSalonId());

        // Generate bill using the price captured at booking time
        BillingEntity bill = new BillingEntity();
        bill.setCustomerId(appt.getCustomerId());
        bill.setSalonId(appt.getSalonId());
        bill.setTotalAmount(appt.getServicePrice() != null ? appt.getServicePrice() : 0.0);
        billingRepo.save(bill);
    }
}
