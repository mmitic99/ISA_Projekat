package isa.FishingBookingApp.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Penalty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    private LocalDateTime receivingDateTime;

    public Penalty() { }

    public Penalty(User user, LocalDateTime receivingDateTime) {
        this.user = user;
        this.receivingDateTime = receivingDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getReceivingDateTime() {
        return receivingDateTime;
    }

    public void setReceivingDateTime(LocalDateTime receivingDateTime) {
        this.receivingDateTime = receivingDateTime;
    }
}
