package com.beuticlick.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.beuticlick.constant.StatusEnum;
import com.beuticlick.entity.Appointment;
import com.beuticlick.entity.BaseSalonEntity;
import com.beuticlick.entity.BillingEntity;
import com.beuticlick.repository.AppointmentRepo;
import com.beuticlick.repository.BillingRepo;

public class AppointmentService extends BaseSalonEntity {

	@Autowired
	private AppointmentRepo repo;

	@Autowired
	InventoryService inventoryService;

	@Autowired
	BillingRepo billingRepo;

	public void completeAppointment(Long appointmentId) {

		Appointment appt = repo.findById(appointmentId).orElseThrow();

		appt.setStatus(StatusEnum.COMPLETED);

		// Reduce stock automatically
		inventoryService.useStock(1L, 10, appt.getSalonId());

		// Generate bill
		BillingEntity bill = new BillingEntity();
		bill.setCustomerId(appt.getCustomerId());
		bill.setTotalAmount(500.0);
		billingRepo.save(bill);
	}
}
