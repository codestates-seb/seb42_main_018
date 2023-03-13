package com.codestates.mainproject.group018.somojeon.join.mapper;

import com.codestates.mainproject.group018.somojeon.join.dto.JoinDto;
import com.codestates.mainproject.group018.somojeon.join.entity.Joins;
import org.hibernate.mapping.Join;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JoinMapper {

    Joins joinPostDtoToJoins(JoinDto.Post requestBody);

    Join joinDecisionPatchDtoToJoins(JoinDto.DecisionPatch requestBody);

    JoinDto.Response joinsToJoinResponseDto(Joins joins);

    JoinDto.DecisionResponse joinsToJoinDecisionResponseDto(Joins joins);

    List<JoinDto.Response> JoinsToJoinResponseDtos(List<Joins> joinsList);
}
