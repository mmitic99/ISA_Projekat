package isa.FishingBookingApp.dto;

public class UserTokenState {
    private String accessToken;
    private Long expiresIn;
    private String role;
    private String mailAddress;
    private Long userId;

    public UserTokenState() {
        this.accessToken = null;
        this.expiresIn = null;
    }

    public UserTokenState(String accessToken, long expiresIn, String role, String mailAddress, Long userId) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.role = role;
        this.mailAddress = mailAddress;
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
