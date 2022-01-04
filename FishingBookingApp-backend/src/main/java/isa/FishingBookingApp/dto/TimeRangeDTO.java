package isa.FishingBookingApp.dto;

import java.time.LocalDateTime;

public class TimeRangeDTO {
    private String startDate;
    private String startTime;
    private LocalDateTime startDateTime;
    private String endDate;
    private String endTime;
    private LocalDateTime endDateTime;

    public TimeRangeDTO() { }

    public TimeRangeDTO(String startDate, String startTime, String endDate, String endTime) {
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
    }

    public void setDateTimesFromStrings() throws Exception {
        this.startDateTime = getDateTimeFromStrings(startDate, startTime);
        this.endDateTime = getDateTimeFromStrings(endDate, endTime);

        if (endDateTime.isBefore(startDateTime)) {
            throw new Exception("Datum je nevalidno unesen.");
        }
    }

    private LocalDateTime getDateTimeFromStrings(String dateString, String timeString) throws Exception {
        try {
            String dateNumbers[] = dateString.split("-");
            int year = Integer.parseInt(dateNumbers[0]);
            int month = Integer.parseInt(dateNumbers[1]);
            int day = Integer.parseInt(dateNumbers[2]);

            String timeNumbers[] = timeString.split(":");

            int hour = Integer.parseInt(timeNumbers[0]);
            int minute = Integer.parseInt(timeNumbers[1]);
            return LocalDateTime.of(year, month, day, hour, minute);
        }catch (Exception e){
            throw new Exception("Datum ili vreme nisu u dobrom formatu ili su nevalidno podešeni");
        }
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }
}
