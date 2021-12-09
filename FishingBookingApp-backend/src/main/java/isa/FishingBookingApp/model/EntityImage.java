package isa.FishingBookingApp.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
public class EntityImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    private ReservationEntities entity;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    byte[] content;

    String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ReservationEntities getEntity() {
        return entity;
    }

    public void setEntity(ReservationEntities entity) {
        this.entity = entity;
    }
}
