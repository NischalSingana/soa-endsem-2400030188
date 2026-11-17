package com.klef.ms.sdp.service;

import com.klef.ms.sdp.entity.Tourist;
import com.klef.ms.sdp.repository.TouristRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristServiceImpl implements TouristService {

    private final TouristRepository touristRepository;

    public TouristServiceImpl(TouristRepository touristRepository) {
        this.touristRepository = touristRepository;
    }

    @Override
    public List<Tourist> getAllTourists() {
        return touristRepository.findAll();
    }

    @Override
    public Tourist getTouristById(Long id) {
        return touristRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tourist not found with id: " + id));
    }

    @Override
    public Tourist createTourist(Tourist tourist) {
        return touristRepository.save(tourist);
    }

    @Override
    public Tourist updateTourist(Long id, Tourist updated) {
        Tourist existing = getTouristById(id);
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setDestination(updated.getDestination());
        return touristRepository.save(existing);
    }

    @Override
    public void deleteTourist(Long id) {
        getTouristById(id); // throws if not found
        touristRepository.deleteById(id);
    }
}
