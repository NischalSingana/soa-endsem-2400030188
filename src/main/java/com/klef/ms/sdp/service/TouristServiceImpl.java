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
}
