package com.codestates.mainproject.group018.somojeon.club.controller;

import com.codestates.mainproject.group018.somojeon.club.dto.UserClubDto;
import com.codestates.mainproject.group018.somojeon.club.entity.UserClub;
import com.codestates.mainproject.group018.somojeon.club.enums.ClubRole;
import com.codestates.mainproject.group018.somojeon.club.mapper.UserClubMapper;
import com.codestates.mainproject.group018.somojeon.club.service.UserClubService;
import com.codestates.mainproject.group018.somojeon.dto.MultiResponseDto;
import com.codestates.mainproject.group018.somojeon.dto.SingleResponseDto;
import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.images.mapper.ImageMapper;
import com.codestates.mainproject.group018.somojeon.user.dto.UserDto;
import com.codestates.mainproject.group018.somojeon.user.mapper.UserMapper;
import com.codestates.mainproject.group018.somojeon.utils.Identifier;
import com.codestates.mainproject.group018.somojeon.utils.UriCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/clubs")
@CrossOrigin(value = {"https://dev.somojeon.site", "https://dev-somojeon.vercel.app"})
@RequiredArgsConstructor
public class UserClubController {

    private final UserClubService userClubService;
    private final UserClubMapper userClubMapper;
    private final UserMapper userMapper;
    private final ImageMapper imageMapper;
    private final Identifier identifier;

    // 소모임 가입 요청
    @PostMapping("{club-id}/joins/{user-id}")
    public ResponseEntity<?> postJoin(@PathVariable("club-id") @Positive Long clubId,
                                      @PathVariable("user-id") @Positive Long userId,
                                      @RequestBody UserClubDto.JoinPost requestBody) {

        requestBody.addClubId(clubId);
        requestBody.addUserId(userId);

        UserClub createdUserClub = userClubService.joinClub(userClubMapper.joinPostToUserClub(requestBody), requestBody.getUserId());
        URI location = UriCreator.createUri("/joins", createdUserClub.getUserClubId());

        return ResponseEntity.created(location).build();
    }

    // 소모임 가입 요청한 전체 유저 조회 (리더만 가능)
    @GetMapping("/{club-id}/joins")
    public ResponseEntity<?> getRequestJoinUsers(@PathVariable("club-id") @Positive Long clubId,
                                                 @Positive Long userId,
                                                 @RequestParam(defaultValue = "1") int page,
                                                 @RequestParam(defaultValue = "100") int size) {
        if (!identifier.checkClubRole(clubId)) {
            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED);
        }
        Page<UserClub> userClubPage = userClubService.findRequestJoinUsers(page - 1, size, userId, clubId);
        List<UserClub> content = userClubPage.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(
                        userClubMapper.userClubsToJoinResponses(content), userClubPage), HttpStatus.OK);
    }

    // 소모임 가입 요청 취소 (가입 신청한 유저 또는 리더만 가능)
    @DeleteMapping("/{club-id}/joins/{user-id}")
    public ResponseEntity<?> deleteJoin(@PathVariable("club-id") @Positive Long clubId,
                                        @PathVariable("user-id") @Positive Long userId) {

//        if (!identifier.isVerified(userId) && identifier.checkClubRole(clubId, "LEADER")) {
//            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED);
//        }
        userClubService.cancelJoin(userId, clubId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 소모임 가입 승인/거절 (리더만 가능)
    @PatchMapping("/{club-id}/joins/{user-id}")
    public ResponseEntity<?> patchDecision(@PathVariable("club-id") @Positive Long clubId,
                                           @PathVariable("user-id") @Positive Long userId,
                                           @RequestBody UserClubDto.JoinDecisionPatch requestBody) {

//        if (!identifier.checkClubRole(clubId, "LEADER")) {
//            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED);
//        }
        requestBody.setClubId(clubId);
        requestBody.setUserId(userId);

        UserClub userClub = userClubService.joinDecision(userId, clubId, requestBody.getJoinStatus());
        UserClubDto.JoinResponse response = userClubMapper.userClubToJoinResponse(userClub);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    // 소모임 회원 탈퇴/추방 (소모임 회원, 리더, 매니저 가능)
    @PatchMapping("{club-id}/memberStatus/{user-id}")
    public ResponseEntity<?> patchClubMemberStatus(@PathVariable("club-id") @Positive Long clubId,
                                                   @PathVariable("user-id") @Positive Long userId,
                                                   @RequestBody UserClubDto.MemberStatusPatch requestBody) {

//        if (!identifier.getClubIds().equals(userId) || identifier.checkClubRole(clubId)) {
//            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED);
//        }
        requestBody.setClubId(clubId);
        requestBody.setUserId(userId);
        UserClub userClub = userClubService.rejectUserClub(userId, clubId, requestBody.getClubMemberStatus());
        UserClubDto.UserClubMemberResponse response = userClubMapper.userClubToUserClubMemberResponse(userClub);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);

    }

    // 소모임 회원 등급 설정 (리더, 매니저만 가능)
    @PatchMapping("/{club-id}/clubRole/{user-id}")
    public ResponseEntity<?> patchClubRole(@PathVariable("club-id") @Positive Long clubId,
                                           @PathVariable("user-id") @Positive Long userId,
                                           @RequestBody UserClubDto.ClubRolePatch requestBody) {

//        if (!identifier.checkClubRole(clubId)) {
//            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED);
//        }

        requestBody.setClubId(clubId);
        requestBody.setUserId(userId);
        UserClub userClub = userClubService.updateClubRole(userId, clubId, requestBody.getClubRole());
        UserClubDto.JoinResponse response = userClubMapper.userClubToJoinResponse(userClub);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    // 소모임장 위임
    @PatchMapping("/{club-id}/delegate")
    public ResponseEntity<LinkedHashMap<Object, Object>> patchDelegateLeader(@PathVariable("club-id") @Positive Long clubId,
                                                                             @RequestParam Long leaderId,
                                                                             @RequestParam ClubRole leaderChangeClubRole,
                                                                             @RequestParam Long memberId,
                                                                             @RequestParam ClubRole memberChangeClubRole) {

        userClubService.changeClubLeader(leaderId, memberId, clubId, leaderChangeClubRole, memberChangeClubRole);
        LinkedHashMap<Object, Object> response = new LinkedHashMap<>();
        response.put("leaderId", leaderId);
        response.put("leaderChangeClubRole", leaderChangeClubRole);
        response.put("memberId", memberId);
        response.put("memberChangeClubRole", memberChangeClubRole);

        return ResponseEntity.ok().body(response);

    }

    // 소모임 안에 멤버 목록 조회
//    @GetMapping("{club-id}/members")
//    public ResponseEntity<?> getClubMembers(@PathVariable("club-id") @Positive Long clubId,
//                                            @RequestParam(defaultValue = "1") int page,
//                                            @RequestParam(defaultValue = "10") int size) {
//        Page<UserClub> clubMembersPage = userClubService.findAllMembersByClubMemberStatus(page - 1, size, clubId);
//        List<UserClub> content = clubMembersPage.getContent();
//
//        return new ResponseEntity<>(
//                new MultiResponseDto<>(
//                        userClubMapper.userClubsToUserClubMembersResponses(content, userMapper, imageMapper), clubMembersPage), HttpStatus.OK);
//    }


    // 소모임 안에 멤버들 기록 조회
    @GetMapping("/{club-id}/members")
    public ResponseEntity getClubUsers(@RequestParam (defaultValue = "1") int page,
                                       @RequestParam (defaultValue = "100") int size,
                                       @PathVariable("club-id") @Positive Long clubId) {
        if(!identifier.isAdmin() && !identifier.getClubIds().contains(clubId)){
            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED);
        }

        Page<UserClub> pageUserClubs = userClubService.getClubMembers(page - 1, size, clubId);
        List<UserClub> userClubs = pageUserClubs.getContent();


        List<UserDto.ResponseWithClub> response = userClubs.stream().map(
                userMapper::userToUserResponseWithClub).collect(Collectors.toList());
        return new ResponseEntity<>(new MultiResponseDto<>(response, pageUserClubs),
                HttpStatus.OK);
    }
}
