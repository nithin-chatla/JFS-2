package com.campusconnect.repository;

import com.campusconnect.model.ClubMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClubMembershipRepository extends JpaRepository<ClubMembership, Long> {
    List<ClubMembership> findByClubId(Long clubId);
    List<ClubMembership> findByStudentId(Long studentId);
    Optional<ClubMembership> findByClubIdAndStudentId(Long clubId, Long studentId);
    boolean existsByClubIdAndStudentId(Long clubId, Long studentId);
    long countByClubIdAndStatus(Long clubId, String status);
}
