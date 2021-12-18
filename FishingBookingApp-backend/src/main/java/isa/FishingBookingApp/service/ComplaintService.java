package isa.FishingBookingApp.service;

import isa.FishingBookingApp.dto.ComplaintDTO;
import isa.FishingBookingApp.model.Complaint;

public interface ComplaintService {
    Complaint addComplaint(ComplaintDTO complaintDTO);
}
