package com.codestates.mainproject.group018.somojeon.club.mapper;

import com.codestates.mainproject.group018.somojeon.club.dto.ClubDto;
import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import com.codestates.mainproject.group018.somojeon.tag.dto.TagDto;
import com.codestates.mainproject.group018.somojeon.tag.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClubMapper {

    Club clubPostDtoToClub(ClubDto.Post requestBody);
    Club clubPatchDtoToClub(ClubDto.Patch requestBody);

//    @Mapping(source = "isPrivate", target = "isPrivate")
    ClubDto.Response clubToClubResponseDto(Club club);

    ClubDto.GetResponse clubToClubGetResponseDto(Club club);

    List<ClubDto.GetResponse> clubToClubGetResponseDtos(List<Club> clubs);


//    default List<TagDto.Response> tagsToTagResponseDtos(List<Tag> tagList) {
//        return tagList.stream()
//                .map(tag -> {
//                    TagDto.Response response = new TagDto.Response();
//                    response.setTagId(tag.getTagId());
//                    response.setTagName(tag.getTagName());
//                    return response;
//                }).collect(Collectors.toList());
//    }

//    default ClubDto.Response clubToClubResponseDto(Club club) {
//        if (club == null) {
//            return null;
//        }
//        return ClubDto.Response.builder()
//                .clubId(club.getClubId())
//                .clubName(club.getClubName())
//                .content(club.getContent())
//                .local(club.getLocal())
//                .categoryName(club.getCategoryName())
//                .viewCount(club.getViewCount())
//                .memberCount(club.getMemberCount())
//                .tagList(tagsToTagResponseDtos(club.getTagList()))
//                .isPrivate(club.isPrivate())
//                .createdAt(club.getCreatedAt())
//                .build();
//    }

}
