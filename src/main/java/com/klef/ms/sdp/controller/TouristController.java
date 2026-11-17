package com.klef.ms.sdp.controller;

import com.klef.ms.sdp.entity.Tourist;
import com.klef.ms.sdp.service.TouristService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tourists")
public class TouristController {

    private final TouristService touristService;

    @Value("${app.api-token}")
    private String validToken;

    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    @GetMapping
    public ResponseEntity<?> getAllTourists(
            @RequestHeader(value = "token", required = false) String token) {

        if (token == null || !token.equals(validToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Unauthorized: Invalid or missing token");
        }

        List<Tourist> tourists = touristService.getAllTourists();
        return ResponseEntity.ok(tourists);
    }
}
