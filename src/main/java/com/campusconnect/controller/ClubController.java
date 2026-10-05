package com.campusconnect.controller;

import com.campusconnect.model.Club;
import com.campusconnect.model.ClubMembership;
import com.campusconnect.service.ClubService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clubs")
public class ClubController {

    private final ClubService clubService;

    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping
    public ResponseEntity<List<Club>> getAllClubs(@RequestParam(required = false) String category) {
        return ResponseEntity.ok(clubService.getAllClubs(category));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Club> getClubById(@PathVariable Long id) {
        return clubService.getClubById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Club> createClub(@RequestBody Club club) {
        return ResponseEntity.ok(clubService.createClub(club));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Club> updateClub(@PathVariable Long id, @RequestBody Club club) {
        return ResponseEntity.ok(clubService.updateClub(id, club));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClub(@PathVariable Long id) {
        clubService.deleteClub(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinClub(@RequestBody ClubMembership membership) {
        try {
            ClubMembership joined = clubService.joinClub(membership);
            return ResponseEntity.ok(joined);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<List<ClubMembership>> getClubMembers(@PathVariable Long id) {
        return ResponseEntity.ok(clubService.getMembershipsByClub(id));
    }

    @GetMapping("/student/{studentId}/memberships")
    public ResponseEntity<List<ClubMembership>> getStudentMemberships(@PathVariable Long studentId) {
        return ResponseEntity.ok(clubService.getMembershipsByStudent(studentId));
    }

    @PutMapping("/members/{membershipId}/status")
    public ResponseEntity<ClubMembership> updateMembershipStatus(
            @PathVariable Long membershipId,
            @RequestParam String status) {
        return ResponseEntity.ok(clubService.updateMembershipStatus(membershipId, status));
    }
}
