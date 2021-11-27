package isa.FishingBookingApp.model;

import javax.persistence.*;

@Entity
public class UserRole {
    @Id
    @SequenceGenerator(name = "roleGen", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roleGen")
    @Column(name="role_id", unique = true, nullable = false)
    private Long id;

    @Column(name="role_name", unique = true, nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
