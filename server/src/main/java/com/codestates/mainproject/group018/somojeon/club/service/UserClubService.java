package com.codestates.mainproject.group018.somojeon.club.service;


import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import com.codestates.mainproject.group018.somojeon.club.entity.UserClub;
import com.codestates.mainproject.group018.somojeon.club.enums.ClubMemberStatus;
import com.codestates.mainproject.group018.somojeon.club.enums.ClubRole;
import com.codestates.mainproject.group018.somojeon.club.enums.JoinStatus;
import com.codestates.mainproject.group018.somojeon.club.repository.UserClubRepository;
import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.user.service.UserService;
import com.codestates.mainproject.group018.somojeon.utils.Identifier;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserClubService {

    private final UserClubRepository userClubRepository;
    private final ClubService clubService;
    private final Identifier identifier;
    private final UserService userService;

    /*
    리더 검증은 컨트롤러에서 한다.
     */

    // 소모임 가입 요청
    public UserClub joinClub(UserClub userClub, Long userId) {
        clubService.findVerifiedClub(userClub.getClub().getClubId());
        userService.findVerifiedUser(userClub.getUser().getUserId());

//        existsUserClubByUserIdAndClubId(userClub.getUser().getUserId(), userClub.getClub().getClubId());

        if (userClub.getUser().getUserId() == userId) {
            throw new BusinessLogicException(ExceptionCode.JOIN_EXISTS);
        }

        if (userClub.getJoinCount() > 6) {
            userClub.setJoinStatus(JoinStatus.BANISHED);
            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED_JOIN_THIS_CLUB);
        } else {
            userClub.setJoinStatus(JoinStatus.PENDING);
            userClub.setJoinCount(userClub.getJoinCount() + 1);
        }

        return userClubRepository.save(userClub);
    }


    // 소모임 가입 요청한 전체 유저 조회 (리더만 가능)
    public Page<UserClub> findRequestJoinUsers(int page, int size, Long clubId) {
        clubService.findVerifiedClub(clubId);
        return userClubRepository.findAll(
                PageRequest.of(page, size, Sort.by("userClubId").descending()));
    }

    // 소모임 가입 요청 취소 (가입 신청한 유저 또는 리더만 가능)
    public void cancelJoin(Long userId, Long clubId) {
        // 클럽 확인.
        clubService.findVerifiedClub(clubId);
        UserClub userClub = findUserClubByUserIdAndClubId(userId, clubId);
        // 가입 대기 중인 상태 확인.
        if (userClub.getJoinStatus() != JoinStatus.PENDING) {
            throw new BusinessLogicException(ExceptionCode.JOIN_NOT_FOUND);
        }

        userClubRepository.deleteById(userClub.getUserClubId());
    }

    // 소모임 가입 승인/거절 (리더만 가능)
    public UserClub joinDecision(Long userId, Long clubId, JoinStatus joinStatus) {
        clubService.findVerifiedClub(clubId);

        UserClub userClub = findUserClubByUserIdAndClubId(userId, clubId);
        if (userClub.getJoinStatus() != JoinStatus.PENDING) {
            throw new BusinessLogicException(ExceptionCode.JOIN_NOT_FOUND);
        }
        userClub.setJoinStatus(joinStatus);

        // 가입 승인시 기본 클럽 ROLE Member
        if (joinStatus == JoinStatus.CONFIRMED) {
            userClub.setClubRole(ClubRole.MEMBER);
            userClub.setClubMemberStatus(ClubMemberStatus.MEMBER_ACTIVE);
        }

        return userClubRepository.save(userClub);

    }

    // 소모임 회원 탈퇴/추방 (소모임 회원, 리더, 매니저 가능)
    public UserClub rejectUserClub(Long userId, Long clubId, ClubMemberStatus clubMemberStatus) {
        clubService.findVerifiedClub(clubId);

        UserClub userClub = findUserClubByUserIdAndClubId(userId, clubId);

        if (userClub.getClubMemberStatus() != ClubMemberStatus.MEMBER_ACTIVE) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }

        userClub.setClubMemberStatus(clubMemberStatus);
        return userClubRepository.save(userClub);
    }

    // 소모임 회원 등급 설정
    public UserClub updateClubRole(Long userId, Long clubId, ClubRole clubRole) {
        clubService.findVerifiedClub(clubId);

        UserClub userClub = findUserClubByUserIdAndClubId(userId, clubId);

        if (userClub.getClubMemberStatus() != ClubMemberStatus.MEMBER_ACTIVE) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }

        userClub.setClubRole(clubRole);
        return userClubRepository.save(userClub);
    }

    // TODO-DW: 소모임장 위임

    // 소모임 안에 활동중인 멤버 목록 조회
//    public Page<UserClub> findAllMembersByClubMemberStatus(int page, int size, Long clubId) {
//        clubService.findVerifiedClub(clubId);
//        return userClubRepository.findByClubMemberStatus(
//                PageRequest.of(page, size, Sort.by("userClubId")), ClubMemberStatus.MEMBER_ACTIVE);
//    }

    // 소모임 안에 멤버들 기록 조회
    public Page<UserClub> findUsers(int page, int size, long clubId) {
        Page<UserClub> userClubPage = clubService.getClubMembers(PageRequest.of(page, size, Sort.by("winRate")) , clubId);

        return userClubPage;
    }

    public void existsUserClubByUserIdAndClubId(Long userId, Long clubId) {
        Optional<UserClub> optionalUserClub = userClubRepository.findByUserIdAndClubId(userId, clubId);
        if (optionalUserClub.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.CLUB_MEMBER_EXISTS);
        }
    }

    public UserClub findUserClubByUserIdAndClubId(Long userId, Long clubId) {
        Optional<UserClub> optionalUserClub = userClubRepository.findByUserIdAndClubId(userId, clubId);
        return optionalUserClub.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_CLUB_NOT_FOUND));
    }
}
