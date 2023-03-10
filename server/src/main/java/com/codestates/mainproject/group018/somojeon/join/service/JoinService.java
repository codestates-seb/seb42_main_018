package com.codestates.mainproject.group018.somojeon.join.service;

import com.codestates.mainproject.group018.somojeon.join.entity.Joins;
import com.codestates.mainproject.group018.somojeon.join.repository.JoinRepository;
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
        //TODO: 회원검증
        return joinRepository.save(joins);
    }

    // 소모임 가입 요청 전체 조회
    public Page<Joins> getJoins(int page, int size, Long joinsId) {
        Pageable pageable = PageRequest.of(page, size);
        return joinRepository.findAllByJoinId(pageable, joinsId);
    }

    // 소모임 가입 요청 취소
    public void deleteJoin(Long joinsId) {
        //TODO: 회원검증
        Joins findJoin = findVerifiedJoin(joinsId);
        joinRepository.delete(findJoin);
    }

    //TODO: user 매핑 후 구현
    public Joins decisionJoin(Joins joins) {
        return null;
    }

    public Joins findVerifiedJoin(Long joinsId) {
        Optional<Joins> findJoin = joinRepository.findById(joinsId);
        Joins joins = findJoin.orElseThrow(() -> new RuntimeException());

        return joins;
    }

}
