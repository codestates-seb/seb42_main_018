package com.codestates.mainproject.group018.somojeon.join.controller;

import com.codestates.mainproject.group018.somojeon.dto.MultiResponseDto;
import com.codestates.mainproject.group018.somojeon.dto.SingleResponseDto;
import com.codestates.mainproject.group018.somojeon.join.dto.JoinDto;
import com.codestates.mainproject.group018.somojeon.join.entity.Joins;
import com.codestates.mainproject.group018.somojeon.join.mapper.JoinMapper;
import com.codestates.mainproject.group018.somojeon.join.service.JoinService;
import com.codestates.mainproject.group018.somojeon.utils.UriCreator;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.mapping.Join;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/joins")
public class JoinController {

    private final JoinService joinService;
    private final JoinMapper mapper;

    public JoinController(JoinService joinService, JoinMapper mapper) {
        this.joinService = joinService;
        this.mapper = mapper;
    }

    // 소모임 가입 요청
    @PostMapping("/{user-id}")
    public ResponseEntity postJoin(@PathVariable ("user-id") Long userId,
                                   @Valid @RequestBody JoinDto.Post requestBody) {

        Joins createdJoin = joinService.createJoin(mapper.joinPostDtoToJoins(requestBody));
        URI location = UriCreator.createUri("/clubs/{user-id}", createdJoin.getJoinsId());

        return ResponseEntity.created(location).build();
    }

    // 소모임 가입 요청 전체 조회
    @GetMapping
    public ResponseEntity getJoins(@RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @Positive Long joinsId) {

        Page<Joins> joinsPage = joinService.getJoins(page, size, joinsId);
        List<Joins> content = joinsPage.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.JoinsToJoinResponseDtos(content), joinsPage), HttpStatus.OK);
    }

    // 소모임 가입 요청 취소
    @DeleteMapping("/{join-id}")
    public ResponseEntity deleteJoin(@PathVariable @Positive Long joinsId) {

        joinService.deleteJoin(joinsId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // 소모임 가입 승인/거절
    @PatchMapping("/{join-id}/decision")
    public ResponseEntity patchDecision(@PathVariable ("join-id") Long joinsId,
                                        @RequestBody JoinDto.DecisionPatch requestBody) {

        Joins findJoin = joinService.decisionJoin(joinsId);
        Join response = mapper.joinDecisionPatchDtoToJoins(requestBody);

        return null;
    }
}
