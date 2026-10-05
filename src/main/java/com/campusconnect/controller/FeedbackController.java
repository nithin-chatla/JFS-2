package com.campusconnect.controller;

import com.campusconnect.model.FeedbackQuery;
import com.campusconnect.service.FeedbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping
    public ResponseEntity<List<FeedbackQuery>> getAllFeedback(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String category) {
        return ResponseEntity.ok(feedbackService.getAllFeedback(status, category));
    }

    @GetMapping("/student")
    public ResponseEntity<List<FeedbackQuery>> getStudentFeedback(@RequestParam String email) {
        return ResponseEntity.ok(feedbackService.getFeedbackByStudent(email));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackQuery> getFeedbackById(@PathVariable Long id) {
        return feedbackService.getFeedbackById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FeedbackQuery> submitFeedback(@RequestBody FeedbackQuery feedback) {
        return ResponseEntity.ok(feedbackService.submitFeedback(feedback));
    }

    @PostMapping("/{id}/respond")
    public ResponseEntity<FeedbackQuery> respondFeedback(
            @PathVariable Long id,
            @RequestBody Map<String, String> payload) {
        String response = payload.get("response");
        String status = payload.getOrDefault("status", "RESOLVED");
        return ResponseEntity.ok(feedbackService.respondFeedback(id, response, status));
    }
}
