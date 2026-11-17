package com.klef.ms.sdp.controller;

import com.klef.ms.sdp.entity.Tourist;
import com.klef.ms.sdp.service.TouristService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tourists")
@Tag(name = "Tourists", description = "CRUD operations for tourist records (token required)")
public class TouristController {

    private final TouristService touristService;

    @Value("${app.api-token}")
    private String validToken;

    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    private boolean isUnauthorized(String token) {
        return token == null || !token.equals(validToken);
    }

    // ── GET all ────────────────────────────────────────────────────────────────

    @Operation(summary = "Get all tourists")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List returned"),
        @ApiResponse(responseCode = "401", description = "Invalid or missing token")
    })
    @GetMapping
    public ResponseEntity<?> getAllTourists(
            @Parameter(description = "API token", required = true)
            @RequestHeader(value = "token", required = false) String token) {

        if (isUnauthorized(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Unauthorized: Invalid or missing token");
        }
        List<Tourist> tourists = touristService.getAllTourists();
        return ResponseEntity.ok(tourists);
    }

    // ── GET by ID ──────────────────────────────────────────────────────────────

    @Operation(summary = "Get a tourist by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tourist found"),
        @ApiResponse(responseCode = "401", description = "Invalid or missing token"),
        @ApiResponse(responseCode = "404", description = "Tourist not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getTouristById(
            @Parameter(description = "API token", required = true)
            @RequestHeader(value = "token", required = false) String token,
            @PathVariable Long id) {

        if (isUnauthorized(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Unauthorized: Invalid or missing token");
        }
        try {
            return ResponseEntity.ok(touristService.getTouristById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ── POST (create) ──────────────────────────────────────────────────────────

    @Operation(summary = "Create a new tourist")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Tourist created"),
        @ApiResponse(responseCode = "401", description = "Invalid or missing token")
    })
    @PostMapping
    public ResponseEntity<?> createTourist(
            @Parameter(description = "API token", required = true)
            @RequestHeader(value = "token", required = false) String token,
            @RequestBody Tourist tourist) {

        if (isUnauthorized(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Unauthorized: Invalid or missing token");
        }
        Tourist created = touristService.createTourist(tourist);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ── PUT (update) ───────────────────────────────────────────────────────────

    @Operation(summary = "Update an existing tourist")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tourist updated"),
        @ApiResponse(responseCode = "401", description = "Invalid or missing token"),
        @ApiResponse(responseCode = "404", description = "Tourist not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTourist(
            @Parameter(description = "API token", required = true)
            @RequestHeader(value = "token", required = false) String token,
            @PathVariable Long id,
            @RequestBody Tourist tourist) {

        if (isUnauthorized(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Unauthorized: Invalid or missing token");
        }
        try {
            return ResponseEntity.ok(touristService.updateTourist(id, tourist));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ── DELETE ─────────────────────────────────────────────────────────────────

    @Operation(summary = "Delete a tourist")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tourist deleted"),
        @ApiResponse(responseCode = "401", description = "Invalid or missing token"),
        @ApiResponse(responseCode = "404", description = "Tourist not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTourist(
            @Parameter(description = "API token", required = true)
            @RequestHeader(value = "token", required = false) String token,
            @PathVariable Long id) {

        if (isUnauthorized(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Unauthorized: Invalid or missing token");
        }
        try {
            touristService.deleteTourist(id);
            return ResponseEntity.ok("Tourist with id " + id + " deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
