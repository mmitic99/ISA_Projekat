package isa.FishingBookingApp.service.impl;

import isa.FishingBookingApp.model.EntityImage;
import isa.FishingBookingApp.repository.EntityImageRepository;
import isa.FishingBookingApp.service.EntityImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityImageServiceImpl implements EntityImageService {
    private EntityImageRepository entityImageRepository;

    @Autowired
    public EntityImageServiceImpl(EntityImageRepository entityImageRepository) {
        this.entityImageRepository = entityImageRepository;
    }

    @Override
    public EntityImage save(EntityImage entityImage) {
        return entityImageRepository.save(entityImage);
    }
}
