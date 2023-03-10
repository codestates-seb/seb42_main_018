package com.codestates.mainproject.group018.somojeon.candidate.controller;

import com.codestates.mainproject.group018.somojeon.candidate.dto.CandidateDto;
import com.codestates.mainproject.group018.somojeon.candidate.entity.Candidate;
import com.codestates.mainproject.group018.somojeon.candidate.mapper.CandidateMapper;
import com.codestates.mainproject.group018.somojeon.candidate.service.CandidateService;
import com.codestates.mainproject.group018.somojeon.dto.MultiResponseDto;
import com.codestates.mainproject.group018.somojeon.dto.SingleResponseDto;
import com.codestates.mainproject.group018.somojeon.utils.UriCreator;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/candidates")
public class CandidateController {
    private final CandidateService candidateService;
    private final CandidateMapper candidateMapper;

    public CandidateController(CandidateService candidateService, CandidateMapper candidateMapper) {
        this.candidateService = candidateService;
        this.candidateMapper = candidateMapper;
    }

    @PostMapping
    public ResponseEntity postCandidate(@Valid @RequestBody CandidateDto.Post requestBody) {
        Candidate candidate = candidateMapper.candidatePostDtoToCandidate(requestBody);

        Candidate createdCandidate = candidateService.createCandidate(candidate);
        URI location = UriCreator.createUri("/candidates", createdCandidate.getCandidateId());

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{candidate-id}")
    public ResponseEntity patchCandidate(@PathVariable("candidate-id") @Positive long candidateId,
                                         @Valid @RequestBody CandidateDto.Patch requestBody) {
        requestBody.addCandidateId(candidateId);

        Candidate candidate = candidateService.updateCandidate(candidateMapper.candidatePatchDtoToCandidate(requestBody));

        return new ResponseEntity<>(
                new SingleResponseDto<>(candidateMapper.candidateToCandidateResponseDto(candidate)), HttpStatus.OK);
    }

    @GetMapping("/{candidate-id}")
    public ResponseEntity getCandidate(@PathVariable("candidate-id") @Positive long candidateId) {
        Candidate candidate = candidateService.findCandidate(candidateId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(candidateMapper.candidateToCandidateResponseDto(candidate)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getCandidates(@RequestParam("page") int page,
                                        @RequestParam("size") int size) {
        Page<Candidate> pageCandidates = candidateService.findCandidates(page - 1, size);
        List<Candidate> candidates = pageCandidates.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(candidateMapper.candidatesToCandidateResponseDtos(candidates), pageCandidates),
                HttpStatus.OK);
    }

    @DeleteMapping("/{candidate-id}")
    public ResponseEntity deleteCandidate(@PathVariable("candidate-id") @Positive long candidateId) {
        candidateService.deleteCandidate(candidateId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
