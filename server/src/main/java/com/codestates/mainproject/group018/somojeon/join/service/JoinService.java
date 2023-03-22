package com.codestates.mainproject.group018.somojeon.join.service;

import com.codestates.mainproject.group018.somojeon.club.entity.UserClub;
import com.codestates.mainproject.group018.somojeon.club.enums.ClubRole;
import com.codestates.mainproject.group018.somojeon.club.service.ClubService;
import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import com.codestates.mainproject.group018.somojeon.join.entity.Joins;
import com.codestates.mainproject.group018.somojeon.join.enums.JoinDecisionStatus;
import com.codestates.mainproject.group018.somojeon.join.repository.JoinRepository;
import com.codestates.mainproject.group018.somojeon.user.repository.UserRepository;
import com.codestates.mainproject.group018.somojeon.user.service.UserService;
import com.codestates.mainproject.group018.somojeon.utils.Identifier;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final JoinRepository joinRepository;
    private final ClubService clubService;
    private final Identifier identifier;
    private final UserService userService;
    private final UserRepository userRepository;


    // 소모임 가입 요청
    public Joins createJoins(Joins joins, Long userId, Long clubId) {

        Club club = clubService.findVerifiedClub(clubId);
        User user = userService.findVerifiedUser(userId);
            joins.setClub(club);
            joins.setUser(user);
        if (joins.getUser().getJoinCount() < 5) {
            joins.setJoinDecisionStatus(JoinDecisionStatus.PENDING);
            joins.getUser().setJoinCount(joins.getUser().getJoinCount() + 1);
        } else {
            joins.setJoinDecisionStatus(JoinDecisionStatus.BANISHED);
            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED_JOIN_THIS_CLUB);
        }

        return joinRepository.save(joins);
    }

    // 소모임 가입 요청 전체 조회 (리더만 가능)
    public Page<Joins> getJoins(int page, int size, Long joinsId, Long clubId) {
        // 리더인지 검증
        identifier.checkClubRole(clubId, "LEADER");
        Pageable pageable = PageRequest.of(page, size);
        return joinRepository.findAllByJoinId(pageable, joinsId);
    }

    // 소모임 가입 요청 취소 (유저만 가능)
    public void deleteJoin(Long joinsId, Long clubId) {
        //User 인지 검증
        identifier.getUserId();
        // 가입 요청한 클럽이 있는지 확인.
        clubService.findVerifiedClub(clubId);

        Joins findJoin = findVerifiedJoin(joinsId);
        joinRepository.delete(findJoin);
    }

    // 소모임 가입 승인/거절 (리더만 가능)
    public Joins joinDecision(Joins joins, Long clubId) {
        // 리더인지 검증
        identifier.checkClubRole(clubId, "LEADER");

        Joins findJoin = findVerifiedJoin(joins.getJoinsId());

        if (findJoin.getJoinDecisionStatus().getStatus().equals("가입 승인")) {
            findJoin.setJoinDecisionStatus(JoinDecisionStatus.CONFIRMED);
            for (UserClub userClub : joins.getClub().getUserClubList()) {
                userClub.getClubRole().setRoles(String.valueOf(ClubRole.Member));
            }
        } else {
            findJoin.setJoinDecisionStatus(JoinDecisionStatus.REFUSED);
        }
       return joinRepository.save(findJoin);
    }

    public Joins findVerifiedJoin(Long joinsId) {
        Optional<Joins> findJoin = joinRepository.findById(joinsId);
        Joins joins = findJoin.orElseThrow(() ->
         new BusinessLogicException(ExceptionCode.JOIN_NOT_FOUND));

        return joins;
    }
}
