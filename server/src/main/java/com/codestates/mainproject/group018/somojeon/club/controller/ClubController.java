package com.codestates.mainproject.group018.somojeon.club.controller;

import com.codestates.mainproject.group018.somojeon.club.dto.ClubDto;
import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import com.codestates.mainproject.group018.somojeon.club.mapper.ClubMapper;
import com.codestates.mainproject.group018.somojeon.club.service.ClubService;
import com.codestates.mainproject.group018.somojeon.dto.SingleResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Slf4j
@RestController
@RequestMapping("/clubs")
public class ClubController {

    private final ClubService clubService;
    private final ClubMapper mapper;

    public ClubController(ClubService clubService, ClubMapper mapper) {
        this.clubService = clubService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postClub(@Valid @RequestBody ClubDto.Post requestBody) {

        Club response = clubService.createClub(mapper.clubPostDtoToClub(requestBody),
                requestBody.getCategoryName(), requestBody.getTagName());

        return new ResponseEntity<>(mapper.clubResponseDtoToClub(response), HttpStatus.CREATED);
    }

    @PatchMapping("/{club-id}")
    public ResponseEntity patchClub(@PathVariable("club-id") @Positive Long clubId,
                                    @RequestBody @Valid ClubDto.Patch requestBody) {

        Club response = clubService.updateClub(
                mapper.clubPatchDtoToClub(requestBody), requestBody.getTagName());

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.clubResponseDtoToClub(response)), HttpStatus.OK);
    }

    @GetMapping("/{club-id}")
    public ResponseEntity getClub() {
        return null;
    }

    @GetMapping
    public ResponseEntity getClubs() {
        return null;
    }

    @GetMapping("/my")
    public ResponseEntity getMyClub() {
        return null;
    }

    @GetMapping("/search")
    public ResponseEntity searchClub() {
        return null;
    }

    @DeleteMapping("/{club-id}")
    public ResponseEntity deleteClub() {
        return null;
    }

    @PatchMapping("/{club-id}/club-status")
    public ResponseEntity patchClubStatus() {
        return null;
    }

    @PatchMapping("/manage/{user-id}")
    public ResponseEntity patchMange() {
        return null;
    }

    @PatchMapping("/manage/{user-id}/leader")
    public ResponseEntity patchClubLeader() {
        return null;
    }

    @PatchMapping("/{user-id}/member-status")
    public ResponseEntity patchClubMemberStatus() {
        return null;
    }

    @PatchMapping("/{user-id}/member-quit")
    public ResponseEntity patchMemberQuitClub() {
        return null;
    }


}
