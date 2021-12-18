package isa.FishingBookingApp.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ReservationDTO {
    private String mailAddress;
    private int days;
    private int guests;
    private String date;
    private String time;
    private LocalDateTime dateTime;
    private Long reservationEntitiesId;
    private List<Long> additionalServicesId;

    public ReservationDTO(String mailAddress, int days, int guests, String date, String time, LocalDateTime dateTime, Long reservationEntitiesId, List<Long> additionalServicesId) throws Exception {
        this.mailAddress = mailAddress;
        this.days = days;
        this.guests = guests;
        this.date = date;
        this.time = time;
        this.dateTime = dateTime;
        this.reservationEntitiesId = reservationEntitiesId;
        this.dateTime = getDateTimeFromStrings(date, time);
        this.additionalServicesId = additionalServicesId;
    }

    public ReservationDTO() {
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
            throw new Exception("Datum ili vreme nisu u dobrom formatu");
        }
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTimeFromStrings() throws Exception {
        this.dateTime = getDateTimeFromStrings(date, time);
    }

    public Long getReservationEntitiesId() {
        return reservationEntitiesId;
    }

    public void setReservationEntitiesId(Long reservationEntitiesId) {
        this.reservationEntitiesId = reservationEntitiesId;
    }

    public List<Long> getAdditionalServicesId() {
        return additionalServicesId;
    }

    public void setAdditionalServicesId(List<Long> additionalServicesId) {
        this.additionalServicesId = additionalServicesId;
    }
}
