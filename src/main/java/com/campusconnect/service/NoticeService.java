package com.campusconnect.service;

import com.campusconnect.model.Notice;
import com.campusconnect.repository.NoticeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public List<Notice> getAllNotices(String category, String audience) {
        if (category != null && !category.trim().equalsIgnoreCase("ALL")) {
            return noticeRepository.findByCategory(category.trim().toUpperCase());
        }
        if (audience != null && !audience.trim().equalsIgnoreCase("ALL")) {
            return noticeRepository.findByTargetAudienceIn(Arrays.asList("ALL", audience.trim().toUpperCase()));
        }
        return noticeRepository.findAllByOrderByIsPinnedDescPublishedDateDesc();
    }

    public Optional<Notice> getNoticeById(Long id) {
        return noticeRepository.findById(id);
    }

    public Notice createNotice(Notice notice) {
        notice.setPublishedDate(LocalDateTime.now());
        return noticeRepository.save(notice);
    }

    public Notice updateNotice(Long id, Notice updated) {
        return noticeRepository.findById(id).map(notice -> {
            notice.setTitle(updated.getTitle());
            notice.setContent(updated.getContent());
            notice.setCategory(updated.getCategory());
            notice.setPriority(updated.getPriority());
            notice.setTargetAudience(updated.getTargetAudience());
            notice.setPinned(updated.isPinned());
            return noticeRepository.save(notice);
        }).orElseThrow(() -> new RuntimeException("Notice not found with id " + id));
    }

    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }
}
