package isa.FishingBookingApp.dto;

public class SubscriptionDTO {
    private String mailAddress;
    private Long reservationEntityId;
    private boolean subscribe;

    public SubscriptionDTO() {
    }

    public SubscriptionDTO(String mailAddress, Long reservationEntityId, boolean subscribe) {
        this.mailAddress = mailAddress;
        this.reservationEntityId = reservationEntityId;
        this.subscribe = subscribe;
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

    public boolean isSubscribe() {
        return subscribe;
    }

    public void setSubscribe(boolean subscribe) {
        this.subscribe = subscribe;
    }
}
