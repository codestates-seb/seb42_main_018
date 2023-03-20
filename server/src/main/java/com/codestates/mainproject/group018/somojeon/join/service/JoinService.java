package com.codestates.mainproject.group018.somojeon.join.service;

import com.codestates.mainproject.group018.somojeon.club.service.ClubService;
import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.join.entity.Joins;
import com.codestates.mainproject.group018.somojeon.join.enums.JoinDecisionStatus;
import com.codestates.mainproject.group018.somojeon.join.repository.JoinRepository;
import com.codestates.mainproject.group018.somojeon.utils.Identifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JoinService {

    private final JoinRepository joinRepository;
    private final ClubService clubService;
    private final Identifier identifier;

    public JoinService(JoinRepository joinRepository,
                       ClubService clubService, Identifier identifier) {
        this.joinRepository = joinRepository;
        this.clubService = clubService;
        this.identifier = identifier;
    }

    // 소모임 가입 요청
    public Joins createJoin(Joins joins, Long clubId) {
        // TODO-DW: User 인지 검증
        identifier.getUserId();
        // TODO-DW: 클럽이 있는지 확인. 그 클럽에 가입
        clubService.findVerifiedClub(clubId);

        return joinRepository.save(joins);
    }

    // 소모임 가입 요청 전체 조회
    public Page<Joins> getJoins(int page, int size, Long joinsId, Long clubId) {
        identifier.checkClubRole(clubId);
        Pageable pageable = PageRequest.of(page, size);
        return joinRepository.findAllByJoinId(pageable, joinsId);
    }

    // 소모임 가입 요청 취소
    public void deleteJoin(Long joinsId, Long clubId) {
        //TODO-DW: User 인지 검증
        identifier.getUserId();
        //TODO-DW: 가입 요청한 클럽이 있는지 확인.
        clubService.findVerifiedClub(clubId);
        Joins findJoin = findVerifiedJoin(joinsId);
        joinRepository.delete(findJoin);
    }

    // 소모임 가입 승인/거절
    public Joins joinDecision(Joins joins, Long clubId) {
        //TODO-DW: 리더인지 검증
        identifier.checkClubRole(clubId);

        Joins findJoin = findVerifiedJoin(joins.getJoinsId());

        if (findJoin.getJoinDecisionStatus().getStatus().equals("가입 승인")) {
            findJoin.setJoinDecisionStatus(JoinDecisionStatus.CONFIRMED);
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
