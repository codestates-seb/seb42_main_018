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
@RequestMapping
public class ClubController {

    private final static String CLUB_DEFAULT_URL = "/club";
    private final ClubService clubService;
    private final ClubMapper mapper;
    private final Identifier identifier;
    private final ScheduleMapper scheduleMapper;


    // 소모임 생성 (모두 가능)
    @PostMapping("/{user-id}/clubs")
    public ResponseEntity<?> postClub(@Valid @RequestBody ClubDto.Post requestBody,
                                      @PathVariable("user-id") @Positive Long userId) {

        Club createdClub = clubService.createClub(mapper.clubPostDtoToClub(requestBody), userId, requestBody.getTagName());
        URI location = UriCreator.createUri(CLUB_DEFAULT_URL, createdClub.getClubId());

        return ResponseEntity.created(location).build();
    }

//    // 소모임 수정 (소개글, 이미지 등)
//    @PostMapping(path = "/{clubId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<?> patchClub(@PathVariable("clubId") @Positive Long clubId,
//                                       @RequestPart ClubDto.Patch requestPart,
//                                       @RequestPart(value = "clubImage",required = false) MultipartFile multipartFile) throws IOException {
//
//        requestPart.setClubId(clubId);
//        Club response = clubService.updateClub(mapper.clubPatchDtoToClub(requestPart), requestPart.getTagName(), multipartFile);
//
//        return new ResponseEntity<>(
//                new SingleResponseDto<>(mapper.clubToClubResponse(response)), HttpStatus.OK);
//    }

    // 소모임 수정 (소개글, 이미지 등, 리더, 매니저만 가능)
    @PatchMapping(path = "/clubs/{clubId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> patchClub(@PathVariable("clubId") @Positive Long clubId,
                                       @ModelAttribute Club club,
                                       @RequestParam String clubName,
                                       @RequestParam String content,
                                       @RequestParam String local,
                                       @RequestParam List<String> tagName,
                                       @RequestParam boolean isSecret,
                                       @RequestParam(value = "clubImage",required = false) MultipartFile multipartFile) throws IOException {

        if (!identifier.checkClubRole(clubId) && !identifier.isAdmin()) {
            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED);
        }

        Club response = clubService.updateClub(clubId, club, clubName, content, local, tagName, isSecret, multipartFile);

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.clubToClubResponse(response)), HttpStatus.OK);
    }

    // 퍼블릭 소모임 단건 조회
    @GetMapping("/clubs/{club-id}")
    public ResponseEntity<?> getClub(@PathVariable("club-id") @Positive Long clubId) {

        Club findClub = clubService.findClub(clubId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.clubToClubResponse(findClub)), HttpStatus.OK);
    }

    // 퍼블릭 소모임 전체 조회
    @GetMapping("/clubs")
    public ResponseEntity<?> getClubs(@RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "200") int size) {

        Page<Club> clubPage = clubService.findClubs(page - 1, size);
        List<Club> content = clubPage.getContent();
        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.clubToClubResponseDtos(content), clubPage), HttpStatus.OK);
    }

    // 키워드로 퍼블릭 소모임 찾기
    @GetMapping("/clubs/search")
    public ResponseEntity<?> searchClubs(@RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam String keyword) {

        Page<Club> clubPage = clubService.searchClubs(page - 1, size, keyword);
        List<Club> content = clubPage.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.clubToClubResponseDtos(content), clubPage), HttpStatus.OK);
    }

    // 카테고리별로 소모임 조회
    @GetMapping("/clubs/categories")
    public ResponseEntity<?> getClubsByCategoryName(@RequestParam("categoryName") String categoryName,
                                                    @RequestParam(defaultValue = "1") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        Page<Club> clubPage = clubService.findClubsByCategoryName(categoryName, page - 1, size);
        List<Club> content = clubPage.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(
                        mapper.clubToClubResponseDtos(content), clubPage), HttpStatus.OK);
    }

    // 소모임 삭제 (리더만 가능)
    @DeleteMapping("/clubs/{club-id}")
    public ResponseEntity deleteClub(@PathVariable("club-id") @Positive Long clubId) {

        if (!identifier.checkClubRole(clubId, "LEADER") && !identifier.isAdmin()) {
            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED);
        }

        clubService.deleteClub(clubId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // TODO: admin 컨트롤러로 가야함. 소모임 상태 변경
    @PatchMapping("/clubs/{club-id}/club-status")
    public ResponseEntity patchClubStatus() {
        return null;
    }


    // 경기 플레이어 등록
    @PostMapping("/clubs/player")
    public ResponseEntity postPlayers(@Valid @RequestBody ClubDto.PostPlayers requestBody) {
        if(!identifier.checkClubRole(requestBody.getClubId())){
            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED);
        }

        List<UserClub> userClubs =  clubService.updatePlayer(requestBody.getClubId(), requestBody.getPlayerUserIds(), true);


        return new ResponseEntity<>(new SingleResponseDto<>(mapper.userClubsToUserCLubResponses(userClubs))
                , HttpStatus.OK);
    }




}
