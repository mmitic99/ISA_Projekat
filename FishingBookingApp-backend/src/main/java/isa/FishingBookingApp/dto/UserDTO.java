package isa.FishingBookingApp.dto;

import isa.FishingBookingApp.model.Address;
import isa.FishingBookingApp.model.User;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class UserDTO {
    private String mailAddress;
    private String password1;
    private String password2;
    private String name;
    private String surname;
    private String mobileNumber;
    private String street;
    private String number;
    private String city;
    private String postalCode;
    private String country;
    private String userRole;
    private String explanationOfReg;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.mailAddress = user.getMailAddress();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.mobileNumber = user.getMobileNumber();
        this.street = user.getAddress().getStreet();
        this.number = user.getAddress().getNumber();
        this.city = user.getAddress().getCity();
        this.postalCode = user.getAddress().getPostalCode();
        this.country = user.getAddress().getCountry();
        this.userRole = user.getRole().getName();
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getExplanationOfReg() {
        return explanationOfReg;
    }

    public void setExplanationOfReg(String explanationOfReg) {
        this.explanationOfReg = explanationOfReg;
    }
}
