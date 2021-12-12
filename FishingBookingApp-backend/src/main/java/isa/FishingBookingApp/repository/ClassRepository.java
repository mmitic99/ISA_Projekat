package isa.FishingBookingApp.repository;

import isa.FishingBookingApp.model.FishingClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClassRepository extends JpaRepository<FishingClass, Long> {
    @Query("select c from FishingClass c where c.fishingInstructor.id = ?1")
    public List<FishingClass> getAllClassesOfUser(Long id);
}