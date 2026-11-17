package com.klef.ms.sdp.service;

import com.klef.ms.sdp.entity.Tourist;
import java.util.List;

public interface TouristService {
    List<Tourist> getAllTourists();
    Tourist getTouristById(Long id);
    Tourist createTourist(Tourist tourist);
    Tourist updateTourist(Long id, Tourist tourist);
    void deleteTourist(Long id);
}
