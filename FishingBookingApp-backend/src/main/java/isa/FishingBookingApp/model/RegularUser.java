package isa.FishingBookingApp.model;

import javax.persistence.Entity;

@Entity
public class RegularUser extends User {
    public RegularUser() {

    }

    public RegularUser(User user) {
        super(user);
    }
}
