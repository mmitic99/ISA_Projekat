package isa.FishingBookingApp.model;

import javax.persistence.Entity;

@Entity
public class RegularUser extends User {
    public RegularUser() {

    }

    public RegularUser(User user) {
        super(user);
    }

    public RegularUser(String mailAddress, String password, String name, String surname, String mobileNumber, Address address, UserRole role, boolean enabled, boolean verified) {
        super(mailAddress, password, name, surname, mobileNumber, address, role, enabled, verified);
    }
}
