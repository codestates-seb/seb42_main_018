package com.codestates.mainproject.group018.somojeon.club.mapper;

import com.codestates.mainproject.group018.somojeon.club.dto.ClubDto;
import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import com.codestates.mainproject.group018.somojeon.club.entity.ClubTag;
import com.codestates.mainproject.group018.somojeon.tag.dto.TagDto;
import com.codestates.mainproject.group018.somojeon.tag.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClubMapper {

//    @Mapping(source = "categoryId", target = "category.categoryId")
    Club clubPostDtoToClub(ClubDto.Post requestBody);
    Club clubPatchDtoToClub(ClubDto.Patch requestBody);
//    ClubDto.Response clubToClubResponseDto(Club club);
//    ClubDto.GetResponse clubToClubGetResponseDto(Club club);
    List<ClubDto.GetResponse> clubToClubGetResponseDtos(List<Club> clubs);

    default List<TagDto.Response> clubTagsToTagResponse(List<ClubTag> clubTagList) {
        return clubTagList.stream()
                .map(clubTag -> {
                    TagDto.Response tagResponseDto = new TagDto.Response();
                    tagResponseDto.setTagId(clubTag.getTag().getTagId());
                    tagResponseDto.setTagName(clubTag.getTag().getTagName());
                    return tagResponseDto;
                })
                .collect(Collectors.toList());
    }

    default ClubDto.Response clubToClubResponse(Club club) {
        if (club == null) {
            return null;
        }

        return ClubDto.Response
                .builder()
                .clubId(club.getClubId())
                .clubName(club.getClubName())
                .content(club.getContent())
                .local(club.getLocal())
                .categoryName(club.getCategoryName())
                .isPrivate(club.isPrivate())
                .modifiedAt(club.getModifiedAt())
                .tagResponseDtos(clubTagsToTagResponse(club.getClubTagList()))
                .build();
    }
}
