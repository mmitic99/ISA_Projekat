package isa.FishingBookingApp.dto;

public class LoginUser {
    private String mailAddress;
    private String password;

    public LoginUser() {
        super();
    }

    public LoginUser(String username, String password) {
        this.setMailAddress(username);
        this.setPassword(password);
    }

    public String getMailAddress() {
        return this.mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
