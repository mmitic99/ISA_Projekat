package isa.FishingBookingApp.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
public class EntityImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ReservationEntities entity;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] content;

    private String name;

    public EntityImage() { }

    public EntityImage(ReservationEntities entity, byte[] content, String name) {
        this.entity = entity;
        this.content = content;
        this.name = name;
    }

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
