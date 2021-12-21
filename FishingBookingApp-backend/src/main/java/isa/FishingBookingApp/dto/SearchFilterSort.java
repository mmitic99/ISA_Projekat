package isa.FishingBookingApp.dto;

import java.time.LocalDateTime;
import java.util.List;

public class SearchFilterSort {
    private String sort;
    private List<String> types;
    private String search;
    private String mailAddress;
    private int days;
    private int guests;
    private LocalDateTime dateTime;
    private boolean isOldReservation = true;

    public SearchFilterSort(String sort, List<String> types, String search) {
        this.sort = sort;
        this.types = types;
        this.search = search;
    }

    public SearchFilterSort(String sort, List<String> types, String search, String mailAddress) {
        this.sort = sort;
        this.types = types;
        this.search = search;
        this.mailAddress = mailAddress;
    }

    public SearchFilterSort(String sort, List<String> types, String search, String mailAddress, Boolean isOldReservation) {
        this.sort = sort;
        this.types = types;
        this.search = search;
        this.mailAddress = mailAddress;
        this.isOldReservation = isOldReservation;
    }

    public SearchFilterSort(String sort, List<String> types, String search, String dateString, String timeString, int days, int guests) throws Exception {
        this.sort = sort;
        this.types = types;
        this.search = search;
        this.days = days;
        this.guests = guests;
        this.dateTime = getDateTimeFromStrings(dateString, timeString);
        if(dateTime == null){
            throw new Exception("Datum ili vreme nisu u dobrom formatu.");
        }
    }

    public SearchFilterSort(String mailAddress, int days, int guests, LocalDateTime dateTime) {
        this.mailAddress = mailAddress;
        this.days = days;
        this.guests = guests;
        this.dateTime = dateTime;
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

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isOldReservation() {
        return isOldReservation;
    }

    public void setOldReservation(boolean oldReservation) {
        isOldReservation = oldReservation;
    }
}
