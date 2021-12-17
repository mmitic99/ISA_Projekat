package isa.FishingBookingApp.dto;

import java.time.LocalDateTime;

public class ReservationDTO {
    private String mailAddress;
    private int days;
    private int guests;
    private String date;
    private String time;
    private LocalDateTime dateTime;
    private Long reservationEntitiesId;

    public ReservationDTO(String mailAddress, int days, int guests, String date, String time, LocalDateTime dateTime, Long reservationEntitiesId) {
        this.mailAddress = mailAddress;
        this.days = days;
        this.guests = guests;
        this.date = date;
        this.time = time;
        this.dateTime = dateTime;
        this.reservationEntitiesId = reservationEntitiesId;
        this.dateTime = getDateTimeFromStrings(date, time);
    }

    public ReservationDTO() {
    }

    private LocalDateTime getDateTimeFromStrings(String dateString, String timeString) {
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
            return null;
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

    public void setDateTimeFromStrings() {
        this.dateTime = getDateTimeFromStrings(date, time);
    }

    public Long getReservationEntitiesId() {
        return reservationEntitiesId;
    }

    public void setReservationEntitiesId(Long reservationEntitiesId) {
        this.reservationEntitiesId = reservationEntitiesId;
    }
}
