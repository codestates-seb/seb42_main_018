package com.codestates.mainproject.group018.somojeon.club.controller;

import com.codestates.mainproject.group018.somojeon.club.dto.ClubDto;
import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import com.codestates.mainproject.group018.somojeon.club.entity.UserClub;
import com.codestates.mainproject.group018.somojeon.club.mapper.ClubMapper;
import com.codestates.mainproject.group018.somojeon.club.service.ClubService;
import com.codestates.mainproject.group018.somojeon.dto.MultiResponseDto;
import com.codestates.mainproject.group018.somojeon.dto.SingleResponseDto;
import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.images.entity.Images;
import com.codestates.mainproject.group018.somojeon.schedule.mapper.ScheduleMapper;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import com.codestates.mainproject.group018.somojeon.utils.Identifier;
import com.codestates.mainproject.group018.somojeon.utils.UriCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(value = {"https://dev.somojeon.site", "https://dev-somojeon.vercel.app"})
@RequestMapping("/clubs")
public class ClubController {

    private final static String CLUB_DEFAULT_URL = "/club";
    private final ClubService clubService;
    private final ClubMapper mapper;
    private final Identifier identifier;
    private final ScheduleMapper scheduleMapper;


    // 소모임 생성
    @PostMapping
    public ResponseEntity<?> postClub(@Valid @RequestBody ClubDto.Post requestBody) {

        Club createdClub = clubService.createClub(mapper.clubPostDtoToClub(requestBody), requestBody.getTagName());
        URI location = UriCreator.createUri(CLUB_DEFAULT_URL, createdClub.getClubId());

        return ResponseEntity.created(location).build();
    }

    // 소모임 수정 (소개글, 이미지 등)
    @PatchMapping(path = "/{clubId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> patchClub(@PathVariable("clubId") @Positive Long clubId,
                                       @ModelAttribute Club club,
                                       @RequestParam(required = false) String clubName,
                                       @RequestParam(required = false) String content,
                                       @RequestParam(required = false) String local,
                                       @RequestParam(required = false) List<String> tagName,
                                       @RequestParam(required = false) boolean isSecret,
                                       @RequestParam(value = "clubImage",required = false) MultipartFile clubImage) throws IOException {

//        club.setClubId(clubId);
        Club response = clubService.updateClub(clubId, club, clubName, content, local, tagName, isSecret, clubImage);

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.clubToClubResponse(response)), HttpStatus.OK);
    }

    // 퍼블릭 소모임 단건 조회
    @GetMapping("/{club-id}")
    public ResponseEntity<?> getClub(@PathVariable("club-id") @Positive Long clubId) {

        Club findClub = clubService.findClub(clubId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.clubToClubResponse(findClub)), HttpStatus.OK);
    }

    // 퍼블릭 소모임 전체 조회
    @GetMapping
    public ResponseEntity<?> getClubs(@RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "200") int size) {

        Page<Club> clubPage = clubService.findClubs(page - 1, size);
        List<Club> content = clubPage.getContent();
        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.clubToClubResponseDtos(content), clubPage), HttpStatus.OK);
    }

    // 키워드로 퍼블릭 소모임 찾기
    @GetMapping("/search")
    public ResponseEntity<?> searchClubs(@RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam String keyword) {

        Page<Club> clubPage = clubService.searchClubs(page - 1, size, keyword);
        List<Club> content = clubPage.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.clubToClubResponseDtos(content), clubPage), HttpStatus.OK);
    }

    // 카테고리별로 소모임 조회
    @GetMapping("/categories")
    public ResponseEntity<?> getClubsByCategoryName(@RequestParam("categoryName") String categoryName,
                                                    @RequestParam(defaultValue = "1") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        Page<Club> clubPage = clubService.findClubsByCategoryName(categoryName, page - 1, size);
        List<Club> content = clubPage.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(
                        mapper.clubToClubResponseDtos(content), clubPage), HttpStatus.OK);
    }

    // 소모임 삭제
    @DeleteMapping("/{club-id}")
    public ResponseEntity deleteClub(@PathVariable("club-id") @Positive Long clubId) {
        clubService.deleteClub(clubId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 소모임 안에 멤버 목록 조회


    // 내 소모임 찾기
    @GetMapping("/my")
    public ResponseEntity getMyClub() {
        return null;
    }

    // TODO: admin 컨트롤러로 가야함. 소모임 상태 변경
    @PatchMapping("/{club-id}/club-status")
    public ResponseEntity patchClubStatus() {
        return null;
    }


    // 소모임 리더 위임
    @PatchMapping("/manage/{user-id}/leader")
    public ResponseEntity patchClubLeader() {
        return null;
    }


    @PostMapping("/player")
    public ResponseEntity postPlayers(@Valid @RequestBody ClubDto.PostPlayers requestBody) {
        if(!identifier.checkClubRole(requestBody.getClubId())){
            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED);
        }

        List<UserClub> userClubs =  clubService.updatePlayer(requestBody.getClubId(), requestBody.getPlayerUserIds(), true);


        return new ResponseEntity<>(new SingleResponseDto<>(mapper.userClubsToUserCLubResponses(userClubs))
                , HttpStatus.OK);
    }




}
