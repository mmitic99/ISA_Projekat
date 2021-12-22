package isa.FishingBookingApp.dto;

import java.time.LocalDateTime;
import java.util.List;

public class SpecialReservationDTO {
    private String mailAddress;
    private Long reservationEntityId;
    private String startDate;
    private String startTime;
    private int days;
    private LocalDateTime startDateTime;
    private int maxPeople;
    private List<Long> additionalServicesId;
    private double price;
    private String validFromDate;
    private String validFromTime;
    private LocalDateTime validFromDateTime;
    private String validToDate;
    private String validToTime;
    private LocalDateTime validToDateTime;

    public SpecialReservationDTO() { }

    public void setDateTimesFromStrings() throws Exception {
        this.startDateTime = getDateTimeFromStrings(startDate, startTime);
        this.validFromDateTime = getDateTimeFromStrings(validFromDate, validFromTime);
        this.validToDateTime = getDateTimeFromStrings(validToDate, validToTime);

        if (validToDateTime.isBefore(validFromDateTime)) {
            throw new Exception("Datum validnosti akcije je pogrešno podešen.");
        }

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

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public Long getReservationEntityId() {
        return reservationEntityId;
    }

    public void setReservationEntityId(Long reservationEntityId) {
        this.reservationEntityId = reservationEntityId;
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

    public String getValidFromDate() {
        return validFromDate;
    }

    public void setValidFromDate(String validFromDate) {
        this.validFromDate = validFromDate;
    }

    public String getValidFromTime() {
        return validFromTime;
    }

    public void setValidFromTime(String validFromTime) {
        this.validFromTime = validFromTime;
    }

    public LocalDateTime getValidFromDateTime() {
        return validFromDateTime;
    }

    public void setValidFromDateTime(LocalDateTime validFromDateTime) {
        this.validFromDateTime = validFromDateTime;
    }

    public String getValidToDate() {
        return validToDate;
    }

    public void setValidToDate(String validToDate) {
        this.validToDate = validToDate;
    }

    public String getValidToTime() {
        return validToTime;
    }

    public void setValidToTime(String validToTime) {
        this.validToTime = validToTime;
    }

    public LocalDateTime getValidToDateTime() {
        return validToDateTime;
    }

    public void setValidToDateTime(LocalDateTime validToDateTime) {
        this.validToDateTime = validToDateTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
