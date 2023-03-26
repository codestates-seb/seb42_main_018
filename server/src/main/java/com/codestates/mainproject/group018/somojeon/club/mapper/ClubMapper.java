package com.codestates.mainproject.group018.somojeon.club.mapper;

import com.codestates.mainproject.group018.somojeon.club.dto.ClubDto;
import com.codestates.mainproject.group018.somojeon.club.dto.UserClubDto;
import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import com.codestates.mainproject.group018.somojeon.club.entity.ClubTag;
import com.codestates.mainproject.group018.somojeon.club.entity.UserClub;
import com.codestates.mainproject.group018.somojeon.tag.dto.TagDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClubMapper {
    Club clubPostDtoToClub(ClubDto.Post requestBody);

    List<ClubDto.Response> clubToClubResponseDtos(List<Club> clubs);

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
                .memberCount(club.getMemberCount())
                .viewCount(club.getViewCount())
                .isSecret(club.isSecret())
                .clubImage(club.getClubImageUrl())
                .clubStatus(club.getClubStatus())
                .modifiedAt(club.getModifiedAt())
                .tagResponseDtos(clubTagsToTagResponse(club.getClubTagList()))
                .build();
    }

    default UserClubDto.Response userClubToUserCLubResponse(UserClub userClub) {
        if ( userClub == null ) {
            return null;
        }

        UserClubDto.Response.ResponseBuilder response = UserClubDto.Response.builder();

        response.clubId(userClub.getClub().getClubId());
        response.clubRole( userClub.getClubRole() );
        response.clubMemberStatus(userClub.getClubMemberStatus());
        response.level( userClub.getLevel() );
        response.playCount( userClub.getPlayCount() );
        response.winCount( userClub.getWinCount() );
        response.winRate( (int) userClub.getWinRate() );

        return response.build();
    }

    default List<UserClubDto.Response> userClubsToUserCLubResponses(List<UserClub> userClubs){
        if ( userClubs == null ) {
            return null;
        }

        List<UserClubDto.Response> list = new ArrayList<>( userClubs.size() );
        for ( UserClub userClub : userClubs ) {
            list.add( userClubToUserCLubResponse(userClub));
        }

        return list;
    }

}
