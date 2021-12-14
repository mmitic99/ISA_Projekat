package isa.FishingBookingApp.model;

import javax.persistence.*;

@Entity
public class RequestForDeletingAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    private String explanation;
    private boolean notApproved;

    public RequestForDeletingAccount() {
        notApproved = false;
    }

    public RequestForDeletingAccount(User user, String explanation) {
        this.user = user;
        this.explanation = explanation;
        notApproved = false;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public boolean isNotApproved() {
        return notApproved;
    }

    public void setNotApproved(boolean approved) {
        this.notApproved = approved;
    }
}
