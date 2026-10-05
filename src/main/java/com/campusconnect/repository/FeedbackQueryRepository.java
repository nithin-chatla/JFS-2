package com.campusconnect.repository;

import com.campusconnect.model.FeedbackQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FeedbackQueryRepository extends JpaRepository<FeedbackQuery, Long> {
    List<FeedbackQuery> findByStudentEmail(String studentEmail);
    List<FeedbackQuery> findByStatus(String status);
    List<FeedbackQuery> findByCategory(String category);
    List<FeedbackQuery> findAllByOrderBySubmittedAtDesc();
}
