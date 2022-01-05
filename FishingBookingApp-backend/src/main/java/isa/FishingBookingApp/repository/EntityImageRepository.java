package isa.FishingBookingApp.repository;

import isa.FishingBookingApp.model.EntityImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EntityImageRepository extends JpaRepository<EntityImage, Long> {
    @Query("select img from EntityImage img where img.entity.id = ?1")
    List<EntityImage> getImagesOfReservationEntity(Long entityId);

    void deleteAllByEntityId(Long id);
}
