package isa.FishingBookingApp.service.impl;

import isa.FishingBookingApp.dto.ComplaintDTO;
import isa.FishingBookingApp.model.Complaint;
import isa.FishingBookingApp.model.ReservationEntities;
import isa.FishingBookingApp.model.User;
import isa.FishingBookingApp.repository.ComplaintRepository;
import isa.FishingBookingApp.repository.ReservationEntitiesRepository;
import isa.FishingBookingApp.repository.UserRepository;
import isa.FishingBookingApp.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ComplaintServiceImpl implements ComplaintService {
    private ComplaintRepository complaintRepository;
    private UserRepository userRepository;
    private ReservationEntitiesRepository reservationEntitiesRepository;

    @Autowired
    public ComplaintServiceImpl(ComplaintRepository complaintRepository, UserRepository userRepository, ReservationEntitiesRepository reservationEntitiesRepository) {
        this.complaintRepository = complaintRepository;
        this.userRepository = userRepository;
        this.reservationEntitiesRepository = reservationEntitiesRepository;
    }

    @Override
    public Complaint addComplaint(ComplaintDTO complaintDTO) {
        User user = userRepository.findByMailAddress(complaintDTO.getMailAddress());
        ReservationEntities reservationEntities = reservationEntitiesRepository.findReservationEntitiesById(complaintDTO.getEntityId());
        Complaint complaint = new Complaint(complaintDTO.getExplain(), user, reservationEntities, LocalDateTime.now());
        return complaintRepository.save(complaint);
    }
}
