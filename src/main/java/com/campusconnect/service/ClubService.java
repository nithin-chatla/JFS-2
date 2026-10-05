package com.campusconnect.service;

import com.campusconnect.model.Club;
import com.campusconnect.model.ClubMembership;
import com.campusconnect.repository.ClubMembershipRepository;
import com.campusconnect.repository.ClubRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClubService {

    private final ClubRepository clubRepository;
    private final ClubMembershipRepository membershipRepository;

    public ClubService(ClubRepository clubRepository, ClubMembershipRepository membershipRepository) {
        this.clubRepository = clubRepository;
        this.membershipRepository = membershipRepository;
    }

    public List<Club> getAllClubs(String category) {
        if (category != null && !category.trim().equalsIgnoreCase("ALL")) {
            return clubRepository.findByCategory(category.trim().toUpperCase());
        }
        return clubRepository.findAll();
    }

    public Optional<Club> getClubById(Long id) {
        return clubRepository.findById(id);
    }

    public Club createClub(Club club) {
        return clubRepository.save(club);
    }

    public Club updateClub(Long id, Club updated) {
        return clubRepository.findById(id).map(club -> {
            club.setName(updated.getName());
            club.setCategory(updated.getCategory());
            club.setDescription(updated.getDescription());
            club.setCoordinatorName(updated.getCoordinatorName());
            club.setCoordinatorEmail(updated.getCoordinatorEmail());
            club.setFacultyAdvisor(updated.getFacultyAdvisor());
            club.setBannerUrl(updated.getBannerUrl());
            club.setLogoIcon(updated.getLogoIcon());
            club.setStatus(updated.getStatus());
            return clubRepository.save(club);
        }).orElseThrow(() -> new RuntimeException("Club not found with id " + id));
    }

    public void deleteClub(Long id) {
        clubRepository.deleteById(id);
    }

    @Transactional
    public ClubMembership joinClub(ClubMembership membership) {
        if (membershipRepository.existsByClubIdAndStudentId(membership.getClubId(), membership.getStudentId())) {
            throw new RuntimeException("You are already a member or have a pending request for this club!");
        }

        ClubMembership saved = membershipRepository.save(membership);

        // Update club member count
        clubRepository.findById(membership.getClubId()).ifPresent(club -> {
            club.setMemberCount(club.getMemberCount() + 1);
            clubRepository.save(club);
        });

        return saved;
    }

    public List<ClubMembership> getMembershipsByClub(Long clubId) {
        return membershipRepository.findByClubId(clubId);
    }

    public List<ClubMembership> getMembershipsByStudent(Long studentId) {
        return membershipRepository.findByStudentId(studentId);
    }

    public ClubMembership updateMembershipStatus(Long membershipId, String status) {
        return membershipRepository.findById(membershipId).map(mem -> {
            mem.setStatus(status);
            return membershipRepository.save(mem);
        }).orElseThrow(() -> new RuntimeException("Membership not found with id " + membershipId));
    }
}
