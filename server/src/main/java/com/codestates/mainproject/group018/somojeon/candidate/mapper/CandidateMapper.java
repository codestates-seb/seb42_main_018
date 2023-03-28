package com.codestates.mainproject.group018.somojeon.candidate.mapper;

import com.codestates.mainproject.group018.somojeon.candidate.dto.CandidateDto;
import com.codestates.mainproject.group018.somojeon.candidate.entity.Candidate;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CandidateMapper {
    Candidate candidatePostDtoToCandidate(CandidateDto.Post requestBody);
    Candidate candidatePatchDtoToCandidate(CandidateDto.Patch requestBody);
    default CandidateDto.Response candidateToCandidateResponseDto(Candidate candidate) {
        if (candidate == null) {
            return null;
        }

        return CandidateDto.Response
                .builder()
                .candidateId(candidate.getCandidateId())
                .userId(candidate.getUser().getUserId())
                .nickName(candidate.getUser().getNickName())
                .attendance(candidate.getAttendance())
                .build();
    }
    default List<CandidateDto.Response> candidatesToCandidateResponseDtos(List<Candidate> candidates) {
        if (candidates == null) {
            return null;
        }

        List<CandidateDto.Response> list = new ArrayList<>(candidates.size());
        for (Candidate candidate : candidates) {
            list.add(candidateToCandidateResponseDto(candidate));
        }

        return list;
    }
}
