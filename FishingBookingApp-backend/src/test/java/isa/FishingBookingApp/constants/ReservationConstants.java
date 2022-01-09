package isa.FishingBookingApp.constants;

import java.time.LocalDateTime;

public class ReservationConstants {
    public static final LocalDateTime dateTime = LocalDateTime.now().plusDays(16);
    public static final String DATE_FOR_RESERVATION_SEARCH = dateTime.getYear() + "-" + dateTime.getMonthValue() + "-" + dateTime.getDayOfMonth();
    public static final String TIME_FOR_RESERVATION_SEARCH = dateTime.getHour() + ":" + dateTime.getMinute();
    public static final String DAYS_FOR_RESERVATION_SEARCH = "1";
    public static final String GUESTS_FOR_RESERVATION_SEARCH = "1";
    public static final int DB_NUMBER_OF_AVAILABLE_RESERVATION_ENTITIES = 3;


    public static final long RESERVATION_ENTITIES_1_ID = 1L;
    public static final long RESERVATION_ENTITIES_2_ID = 2L;
    public static final LocalDateTime RESERVATION_1_DATE_TIME_START = LocalDateTime.now().minusDays(3);
    public static final double RESERVATION_1_DURATION = 48;
    public static final LocalDateTime RESERVATION_2_DATE_TIME_START = LocalDateTime.now().minusDays(33);
    public static final double RESERVATION_2_DURATION = 240;
    public static final LocalDateTime CHECK_RESERVATION_1_DATE_TIME_START = LocalDateTime.now().plusDays(10);
    public static final LocalDateTime CHECK_RESERVATION_2_DATE_TIME_START = LocalDateTime.now().minusDays(30);
    public static final int RESERVATION_DURATION = 10;


}
