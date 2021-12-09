package isa.FishingBookingApp.service;

import isa.FishingBookingApp.model.EntityImage;

import java.util.List;

public interface EntityImageService {
    EntityImage save(EntityImage entityImage);

    List<EntityImage> getImagesOfReservationEntity(Long entityId);

    EntityImage getOneImageOfReservationEntity(Long entityId);
}
