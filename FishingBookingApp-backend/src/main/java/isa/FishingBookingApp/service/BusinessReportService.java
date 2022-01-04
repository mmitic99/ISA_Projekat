package isa.FishingBookingApp.service;

import isa.FishingBookingApp.dto.IncomeFromEntityDTO;
import isa.FishingBookingApp.dto.TimeRangeDTO;

import java.util.List;

public interface BusinessReportService {
    List<IncomeFromEntityDTO> getIncomeReportForOwnerInTimeRange(Long ownerId, TimeRangeDTO timeRangeDTO);
}
