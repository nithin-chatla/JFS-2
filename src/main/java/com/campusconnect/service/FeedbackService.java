package com.campusconnect.service;

import com.campusconnect.model.FeedbackQuery;
import com.campusconnect.repository.FeedbackQueryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

    private final FeedbackQueryRepository feedbackRepository;

    public FeedbackService(FeedbackQueryRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public List<FeedbackQuery> getAllFeedback(String status, String category) {
        if (status != null && !status.trim().equalsIgnoreCase("ALL")) {
            return feedbackRepository.findByStatus(status.trim().toUpperCase());
        }
        if (category != null && !category.trim().equalsIgnoreCase("ALL")) {
            return feedbackRepository.findByCategory(category.trim().toUpperCase());
        }
        return feedbackRepository.findAllByOrderBySubmittedAtDesc();
    }

    public List<FeedbackQuery> getFeedbackByStudent(String email) {
        return feedbackRepository.findByStudentEmail(email);
    }

    public Optional<FeedbackQuery> getFeedbackById(Long id) {
        return feedbackRepository.findById(id);
    }

    public FeedbackQuery submitFeedback(FeedbackQuery feedback) {
        feedback.setStatus("PENDING");
        feedback.setSubmittedAt(LocalDateTime.now());
        return feedbackRepository.save(feedback);
    }

    public FeedbackQuery respondFeedback(Long id, String response, String status) {
        return feedbackRepository.findById(id).map(query -> {
            query.setAdminResponse(response);
            query.setStatus(status != null ? status : "RESOLVED");
            query.setResolvedAt(LocalDateTime.now());
            return feedbackRepository.save(query);
        }).orElseThrow(() -> new RuntimeException("Feedback query not found with id " + id));
    }
}
