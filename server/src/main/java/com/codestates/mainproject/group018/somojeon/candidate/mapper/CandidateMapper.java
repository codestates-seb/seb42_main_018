package com.codestates.mainproject.group018.somojeon.candidate.mapper;

import com.codestates.mainproject.group018.somojeon.candidate.dto.CandidateDto;
import com.codestates.mainproject.group018.somojeon.candidate.entity.Candidate;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CandidateMapper {
    Candidate candidatePostDtoToCandidate(CandidateDto.Post requestBody);
    Candidate candidatePatchDtoToCandidate(CandidateDto.Patch requestBody);
    CandidateDto.Response candidateToCandidateResponseDto(Candidate candidate);
    List<CandidateDto.Response> candidatesToCandidateResponseDtos(List<Candidate> candidates);
}
