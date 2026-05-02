package com.beuticlick.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.beuticlick.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findBySalonId(Long salonId);

    List<Appointment> findBySalonIdAndCustomerId(Long salonId, Long customerId);

    List<Appointment> findBySalonIdAndStartTimeBetween(Long salonId, LocalDateTime start, LocalDateTime end);

    @Query("""
SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END
FROM Appointment a
WHERE a.staff.id = :staffId
AND a.status = 'BOOKED'
AND (
    a.startTime < :endTime AND a.endTime > :startTime
)
""")
boolean existsOverlappingAppointment(Long staffId,
                                     LocalDateTime startTime,
                                     LocalDateTime endTime);
                                     List<Appointment> findBySalon_Id(Long salonId);

}
