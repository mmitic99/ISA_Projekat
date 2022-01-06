package isa.FishingBookingApp.dto;

import java.time.LocalDateTime;

public class AvailableAppointmentDTO {
    private Long id;
    private Long entityId;
    private String startDateString;
    private String startTimeString;
    private String endDateString;
    private String endTimeString;
    private LocalDateTime fromTime;
    private LocalDateTime toTime;

    public  AvailableAppointmentDTO() { }

    public void setDateTimesFromStrings() {
        this.fromTime = getDateTimeFromStrings(startDateString, startTimeString);
        this.toTime = getDateTimeFromStrings(endDateString, endTimeString);

        if (fromTime.isAfter(toTime)) {
            this.fromTime = null;
            this.toTime = null;
        }
    }

    private LocalDateTime getDateTimeFromStrings(String dateString, String timeString) {
        try {
            String[] dateNumbers = dateString.split("-");
            int year = Integer.parseInt(dateNumbers[0]);
            int month = Integer.parseInt(dateNumbers[1]);
            int day = Integer.parseInt(dateNumbers[2]);

            String[] timeNumbers = timeString.split(":");

            int hour = Integer.parseInt(timeNumbers[0]);
            int minute = Integer.parseInt(timeNumbers[1]);
            return LocalDateTime.of(year, month, day, hour, minute);
        }catch (Exception e){
            return null;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getStartDateString() {
        return startDateString;
    }

    public void setStartDateString(String startDateString) {
        this.startDateString = startDateString;
    }

    public String getStartTimeString() {
        return startTimeString;
    }

    public void setStartTimeString(String startTimeString) {
        this.startTimeString = startTimeString;
    }

    public String getEndDateString() {
        return endDateString;
    }

    public void setEndDateString(String endDateString) {
        this.endDateString = endDateString;
    }

    public String getEndTimeString() {
        return endTimeString;
    }

    public void setEndTimeString(String endTimeString) {
        this.endTimeString = endTimeString;
    }

    public LocalDateTime getFromTime() {
        return fromTime;
    }

    public void setFromTime(LocalDateTime fromTime) {
        this.fromTime = fromTime;
    }

    public LocalDateTime getToTime() {
        return toTime;
    }

    public void setToTime(LocalDateTime toTime) {
        this.toTime = toTime;
    }
}
