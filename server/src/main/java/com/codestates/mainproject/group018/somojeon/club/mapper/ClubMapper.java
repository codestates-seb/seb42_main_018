package com.codestates.mainproject.group018.somojeon.club.mapper;

import com.codestates.mainproject.group018.somojeon.club.dto.ClubDto;
import com.codestates.mainproject.group018.somojeon.club.dto.UserClubDto;
import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import com.codestates.mainproject.group018.somojeon.club.entity.UserClub;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClubMapper {
    default Club clubPostDtoToClub(ClubDto.Post requestBody) {

        if ( requestBody == null ) {
            return null;
        }

        Club club = new Club();

        club.setClubName( requestBody.getClubName() );
        club.setContent( requestBody.getContent() );
        club.setLocal( requestBody.getLocal() );
        club.setCategoryName( requestBody.getCategoryName() );
        club.setClubPrivateStatus(requestBody.getClubPrivateStatus());
        List<String> list = requestBody.getTagList();
        if ( list != null ) {
            club.setTagList( new ArrayList<String>( list ) );
        }

        return club;
    }

    List<ClubDto.Response> clubToClubResponseDtos(List<Club> clubs);

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
                .tagList(club.getTagList())
                .clubPrivateStatus(club.getClubPrivateStatus())
                .memberCount(club.getMemberCount())
                .viewCount(club.getViewCount())
                .clubImage(club.getClubImageUrl())
                .clubStatus(club.getClubStatus())
                .modifiedAt(club.getModifiedAt())
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
