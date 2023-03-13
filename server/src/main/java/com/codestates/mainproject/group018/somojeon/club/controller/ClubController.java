package com.codestates.mainproject.group018.somojeon.club.controller;

import com.codestates.mainproject.group018.somojeon.club.dto.ClubDto;
import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import com.codestates.mainproject.group018.somojeon.club.mapper.ClubMapper;
import com.codestates.mainproject.group018.somojeon.club.service.ClubService;
import com.codestates.mainproject.group018.somojeon.dto.MultiResponseDto;
import com.codestates.mainproject.group018.somojeon.dto.SingleResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

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

    // 소모임 생성
    @PostMapping
    public ResponseEntity postClub(@Valid @RequestBody ClubDto.Post requestBody) {

        Club response = clubService.createClub(mapper.clubPostDtoToClub(requestBody),
                requestBody.getCategoryName(), requestBody.getTagName());

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.clubResponseDtoToClub(response)), HttpStatus.CREATED);
    }

    // 소모임 수정 (소개글, 이미지 등)
    @PatchMapping("/{club-id}")
    public ResponseEntity patchClub(@PathVariable("club-id") @Positive Long clubId,
                                    @RequestBody @Valid ClubDto.Patch requestBody) {

        Club response = clubService.updateClub(
                mapper.clubPatchDtoToClub(requestBody), requestBody.getTagName());

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.clubResponseDtoToClub(response)), HttpStatus.OK);
    }

    // 소모임 단건 조회
    @GetMapping("/{club-id}")
    public ResponseEntity getClub(@PathVariable("club-id") @Positive Long clubId) {

        Club findClub = clubService.findClub(clubId);
        ClubDto.Response response = mapper.clubResponseDtoToClub(findClub);

        return new ResponseEntity<>(
                new SingleResponseDto<>(response), HttpStatus.OK);
    }

    // 소모임 전체 조회
    @GetMapping
    public ResponseEntity getClubs(@RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam int viewCount) {

        Page<Club> clubPage = clubService.findClubs(page, size, viewCount);
        List<Club> content = clubPage.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.clubResponseDtosToClub(content), clubPage), HttpStatus.OK);
    }

    @GetMapping("/my")
    public ResponseEntity getMyClub() {
        return null;
    }

    // 키워드로 소모임 찾기
    @GetMapping("/search")
    public ResponseEntity searchClub(@RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam String keyword) {

        Page<Club> clubPage = clubService.searchClubs(page, size, keyword);
        List<Club> content = clubPage.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.clubResponseDtosToClub(content), clubPage), HttpStatus.OK);
    }

    // 소모임 삭제
    @DeleteMapping("/{club-id}")
    public ResponseEntity deleteClub(@PathVariable("club-id") @Positive Long clubId) {
        clubService.deleteClub(clubId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
