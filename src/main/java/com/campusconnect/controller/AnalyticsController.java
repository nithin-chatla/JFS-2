package com.campusconnect.controller;

import com.campusconnect.dto.DashboardStatsDTO;
import com.campusconnect.service.AnalyticsService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardStatsDTO> getDashboardStats() {
        return ResponseEntity.ok(analyticsService.getDashboardStats());
    }

    @GetMapping("/export/registrations/csv")
    public ResponseEntity<byte[]> exportRegistrationsCsv() {
        String csv = analyticsService.generateRegistrationsCsv();
        byte[] bytes = csv.getBytes(java.nio.charset.StandardCharsets.UTF_8);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=campus_connect_registrations.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(bytes);
    }

    @GetMapping("/export/events/csv")
    public ResponseEntity<byte[]> exportEventsCsv() {
        String csv = analyticsService.generateEventsCsv();
        byte[] bytes = csv.getBytes(java.nio.charset.StandardCharsets.UTF_8);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=campus_connect_events.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(bytes);
    }
}
