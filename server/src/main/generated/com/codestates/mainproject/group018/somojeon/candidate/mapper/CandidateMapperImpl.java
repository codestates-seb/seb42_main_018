package com.codestates.mainproject.group018.somojeon.candidate.mapper;

import com.codestates.mainproject.group018.somojeon.candidate.dto.CandidateDto;
import com.codestates.mainproject.group018.somojeon.candidate.entity.Candidate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-09T16:50:34+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.17 (Azul Systems, Inc.)"
)
@Component
public class CandidateMapperImpl implements CandidateMapper {

    @Override
    public Candidate candidatePostDtoToCandidate(CandidateDto.Post requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Candidate candidate = new Candidate();

        return candidate;
    }

    @Override
    public Candidate candidatePatchDtoToCandidate(CandidateDto.Patch requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Candidate candidate = new Candidate();

        return candidate;
    }

    @Override
    public CandidateDto.Response candidateToCandidateResponseDto(Candidate candidate) {
        if ( candidate == null ) {
            return null;
        }

        CandidateDto.Response response = new CandidateDto.Response();

        return response;
    }

    @Override
    public List<CandidateDto.Response> candidatesToCandidateResponseDtos(List<Candidate> candidates) {
        if ( candidates == null ) {
            return null;
        }

        List<CandidateDto.Response> list = new ArrayList<CandidateDto.Response>( candidates.size() );
        for ( Candidate candidate : candidates ) {
            list.add( candidateToCandidateResponseDto( candidate ) );
        }

        return list;
    }
}
