package com.codestates.mainproject.group018.somojeon.club.mapper;

import com.codestates.mainproject.group018.somojeon.club.dto.ClubDto;
import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClubMapper {

    Club clubPostDtoToClub(ClubDto.Post requestBody);

    Club clubPatchDtoToClub(ClubDto.Patch requestBody);

    ClubDto.Response clubResponseDtoToClub(Club club);

    List<ClubDto.Response> clubResponseDtosToClub(List<Club> clubList);
}
