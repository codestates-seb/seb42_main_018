package com.codestates.mainproject.group018.somojeon.join.service;

import com.codestates.mainproject.group018.somojeon.join.entity.Joins;
import com.codestates.mainproject.group018.somojeon.join.enums.JoinDecisionStatus;
import com.codestates.mainproject.group018.somojeon.join.repository.JoinRepository;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JoinService {

    private final JoinRepository joinRepository;

    public JoinService(JoinRepository joinRepository) {
        this.joinRepository = joinRepository;
    }

    // 소모임 가입 요청
    public Joins createJoin(Joins joins) {
        // TODO-DW: User 인지 검증
        if (!joins.getClub().isPrivate()) {
            joinRepository.save(joins);
        }
        return joins;
    }

    // 소모임 가입 요청 전체 조회
    public Page<Joins> getJoins(int page, int size, Long joinsId) {
        Pageable pageable = PageRequest.of(page, size);
        return joinRepository.findAllByJoinId(pageable, joinsId);
    }

    // 소모임 가입 요청 취소
    public void deleteJoin(Long joinsId) {
        //TODO-DW: User 인지 검증
        Joins findJoin = findVerifiedJoin(joinsId);
        joinRepository.delete(findJoin);
    }

    // 소모임 가입 승인/거절
    public Joins decisionJoin(Long joinsId) {
        //TODO-DW: 리더인지 검증

        Joins findJoin = findVerifiedJoin(joinsId);

        if (findJoin.getJoinDecisionStatus().getStatus().equals("가입 승인")) {
            findJoin.setJoinDecisionStatus(JoinDecisionStatus.CONFIRMED);
        } else {
            findJoin.setJoinDecisionStatus(JoinDecisionStatus.REFUSED);
        }

        return joinRepository.save(findJoin);
    }

    public Joins findVerifiedJoin(Long joinsId) {
        Optional<Joins> findJoin = joinRepository.findById(joinsId);
        Joins joins = findJoin.orElseThrow(() -> new RuntimeException());

        return joins;
    }

}
