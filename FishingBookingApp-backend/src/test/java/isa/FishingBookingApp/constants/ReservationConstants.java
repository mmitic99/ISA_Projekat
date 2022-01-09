package isa.FishingBookingApp.constants;

import java.time.LocalDateTime;

public class ReservationConstants {
    public static final LocalDateTime dateTime = LocalDateTime.now().plusDays(16);
    public static final String DATE_FOR_RESERVATION_SEARCH = dateTime.getYear() + "-" + dateTime.getMonthValue() + "-" + dateTime.getDayOfMonth();
    public static final String TIME_FOR_RESERVATION_SEARCH = dateTime.getHour() + ":" + dateTime.getMinute();
    public static final String DAYS_FOR_RESERVATION_SEARCH = "1";
    public static final String GUESTS_FOR_RESERVATION_SEARCH = "1";
    public static final int DB_NUMBER_OF_AVAILABLE_RESERVATION_ENTITIES = 2;
}
