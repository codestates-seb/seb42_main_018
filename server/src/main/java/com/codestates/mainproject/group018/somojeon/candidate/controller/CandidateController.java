package com.codestates.mainproject.group018.somojeon.candidate.controller;

import com.codestates.mainproject.group018.somojeon.candidate.dto.CandidateDto;
import com.codestates.mainproject.group018.somojeon.candidate.entity.Candidate;
import com.codestates.mainproject.group018.somojeon.candidate.mapper.CandidateMapper;
import com.codestates.mainproject.group018.somojeon.candidate.service.CandidateService;
import com.codestates.mainproject.group018.somojeon.dto.MultiResponseDto;
import com.codestates.mainproject.group018.somojeon.dto.SingleResponseDto;
import com.codestates.mainproject.group018.somojeon.utils.UriCreator;
import lombok.RequiredArgsConstructor;
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
@RequestMapping
@RequiredArgsConstructor
public class CandidateController {
    private final CandidateService candidateService;
    private final CandidateMapper candidateMapper;

    @PostMapping("/clubs/{club-id}/schedules/{schedule-id}/candidates")
    public ResponseEntity postCandidate(@PathVariable("club-id") @Positive long clubId,
                                        @PathVariable("schedule-id") @Positive long scheduleId,
                                        @Valid @RequestBody CandidateDto.Post requestBody) {
        requestBody.addClubId(clubId);
        requestBody.addScheduleId(scheduleId);

        Candidate candidate = candidateMapper.candidatePostDtoToCandidate(requestBody);

        Candidate createdCandidate = candidateService.createCandidate(candidate, clubId, scheduleId);
        URI location = UriCreator.createUri("/candidates", createdCandidate.getCandidateId());

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/clubs/{club-id}/schedules/{schedule-id}/{candidate-id}")
    public ResponseEntity patchCandidate(@PathVariable("club-id") @Positive long clubId,
                                         @PathVariable("schedule-id") @Positive long scheduleId,
                                         @PathVariable("candidate-id") @Positive long candidateId,
                                         @Valid @RequestBody CandidateDto.Patch requestBody) {
        requestBody.addClubId(clubId);
        requestBody.addScheduleId(scheduleId);
        requestBody.addCandidateId(candidateId);

        Candidate candidate = candidateService.updateCandidate(
                candidateMapper.candidatePatchDtoToCandidate(requestBody), clubId, scheduleId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(candidateMapper.candidateToCandidateResponseDto(candidate)), HttpStatus.OK);
    }

    @GetMapping("/clubs/{club-id}/schedules/{schedule-id}/candidates/{candidate-id}")
    public ResponseEntity getCandidate(@PathVariable("club-id") @Positive long clubId,
                                       @PathVariable("schedule-id") @Positive long scheduleId,
                                       @PathVariable("candidate-id") @Positive long candidateId) {
        Candidate candidate = candidateService.findCandidate(candidateId, clubId, scheduleId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(candidateMapper.candidateToCandidateResponseDto(candidate)), HttpStatus.OK);
    }

    @GetMapping("/clubs/{club-id}/schedules/{schedule-id}/candidates")
    public ResponseEntity getCandidates(@PathVariable("club-id") @Positive long clubId,
                                        @PathVariable("schedule-id") @Positive long scheduleId,
                                        @RequestParam("page") int page,
                                        @RequestParam("size") int size) {
        Page<Candidate> pageCandidates = candidateService.findCandidates(clubId,scheduleId, page - 1, size);
        List<Candidate> candidates = pageCandidates.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(candidateMapper.candidatesToCandidateResponseDtos(candidates), pageCandidates),
                HttpStatus.OK);
    }

    @DeleteMapping("/clubs/{club-id}/schedules/{schedule-id}/candidates/{candidate-id}")
    public ResponseEntity deleteCandidate(@PathVariable("club-id") @Positive long clubId,
                                          @PathVariable("schedule-id") @Positive long scheduleId,
                                          @PathVariable("candidate-id") @Positive long candidateId) {
        candidateService.deleteCandidate(candidateId, clubId, scheduleId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
