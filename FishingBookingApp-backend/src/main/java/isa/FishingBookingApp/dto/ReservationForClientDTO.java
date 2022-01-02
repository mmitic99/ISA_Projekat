package isa.FishingBookingApp.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ReservationForClientDTO {
    private String ownerMailAddress;
    private Long reservationEntityId;
    private String userMailAddress;
    private String startDate;
    private String startTime;
    private int days;
    private LocalDateTime startDateTime;
    private int maxPeople;
    private List<Long> additionalServicesId;

    public void setDateTimesFromStrings() throws Exception {
        this.startDateTime = getDateTimeFromStrings(startDate, startTime);

        if (startDateTime.isBefore(LocalDateTime.now())) {
            throw new Exception("Vreme početka rezervacije je nevalidno.");
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

    public String getOwnerMailAddress() {
        return ownerMailAddress;
    }

    public void setOwnerMailAddress(String ownerMailAddress) {
        this.ownerMailAddress = ownerMailAddress;
    }

    public Long getReservationEntityId() {
        return reservationEntityId;
    }

    public void setReservationEntityId(Long reservationEntityId) {
        this.reservationEntityId = reservationEntityId;
    }

    public String getUserMailAddress() {
        return userMailAddress;
    }

    public void setUserMailAddress(String userMailAddress) {
        this.userMailAddress = userMailAddress;
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

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }

    public List<Long> getAdditionalServicesId() {
        return additionalServicesId;
    }

    public void setAdditionalServicesId(List<Long> additionalServicesId) {
        this.additionalServicesId = additionalServicesId;
    }
}
