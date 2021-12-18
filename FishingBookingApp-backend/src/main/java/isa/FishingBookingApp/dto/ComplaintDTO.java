package isa.FishingBookingApp.dto;

public class ComplaintDTO {
    private Long entityId;
    private String explain;
    private String mailAddress;

    public ComplaintDTO() {
    }

    public ComplaintDTO(Long entityId, String explain, String mailAddress) {
        this.entityId = entityId;
        this.explain = explain;
        this.mailAddress = mailAddress;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }
}
