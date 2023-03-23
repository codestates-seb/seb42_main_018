package com.codestates.mainproject.group018.somojeon.club.mapper;

import com.codestates.mainproject.group018.somojeon.club.dto.UserClubDto;
import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import com.codestates.mainproject.group018.somojeon.club.entity.UserClub;
import com.codestates.mainproject.group018.somojeon.images.dto.ImagesResponseDto;
import com.codestates.mainproject.group018.somojeon.images.entity.Images;
import com.codestates.mainproject.group018.somojeon.images.mapper.ImageMapper;
import com.codestates.mainproject.group018.somojeon.user.dto.UserDto;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import com.codestates.mainproject.group018.somojeon.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {UserMapper.class, ImageMapper.class})
public interface UserClubMapper {

    default UserClub joinPostToUserClub(UserClubDto.JoinPost requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        UserClub userClub = new UserClub();
        Club club = new Club();
        club.setClubId(requestBody.getClubId());
        User user = new User();
        user.setUserId(requestBody.getUserId());

        userClub.setClub(club);
        userClub.setUser(user);
        userClub.setContent( requestBody.getContent() );

        return userClub;
    }

    UserClub memberStatusPatchToUserClub(UserClubDto.MemberStatusPatch requestBody);

    default UserClub clubRolPatchToUserClub(UserClubDto.ClubRolePatch requestBody) {
        if (requestBody == null) {
            return null;
        }

        UserClub userClub = new UserClub();
        userClub.setClubRole(requestBody.getClubRole());

        return userClub;
    }

    UserClub joinDecisionPatchToUserClub(UserClubDto.JoinDecisionPatch requestBody);

    default UserClubDto.JoinResponse userClubToJoinResponse(UserClub userClub, UserMapper userMapper, ImageMapper imageMapper) {

        if (userClub == null) {
            return null;
        }

        UserClubDto.JoinResponse joinResponse = new UserClubDto.JoinResponse();
        joinResponse.setUserClubId(userClub.getUserClubId());
        joinResponse.setContent(userClub.getContent());
        userMapper.userToUserClubResponse(userClub.getUser(), imageMapper);

        return joinResponse;
    }

    default List<UserClubDto.JoinResponse> userClubsToJoinResponses(List<UserClub> userClubList, UserMapper userMapper, ImageMapper imageMapper) {
        if ( userClubList == null ) {
            return null;
        }

        List<UserClubDto.JoinResponse> list = new ArrayList<UserClubDto.JoinResponse>( userClubList.size() );
        for ( UserClub userClub : userClubList ) {
            list.add( userClubToJoinResponse( userClub, userMapper, imageMapper) );
        }

        return list;
    }


    // TODO-JH : 제훈님 여기꺼 갖다 쓰셔도 될꺼같습니다. ClubMapper 말구요~
    UserClubDto.Response userClubToUserClubResponse(UserClub userClub);

    default List<UserClubDto.Response> userClubsToUserCLubResponses(List<UserClub> userClubs){
        return userClubs.stream()
                .map(this::userClubToUserClubResponse)
                .collect(Collectors.toList());
    };


    default UserClubDto.UserClubMemberResponse userClubToUserClubMemberResponse(UserClub userClub, UserMapper userMapper, ImageMapper imageMapper) {
        if (userClub == null) {
            return null;
        }

        UserClubDto.UserClubMemberResponse userClubMemberResponse = new UserClubDto.UserClubMemberResponse();

        userClubMemberResponse.setUserClubId(userClub.getUserClubId());
        userClubMemberResponse.setClubMemberStatus(userClub.getClubMemberStatus());
        userClubMemberResponse.setClubRole(userClub.getClubRole());
        userMapper.userToUserClubResponse(userClub.getUser(), imageMapper);

        return userClubMemberResponse;
    }
    default List<UserClubDto.UserClubMemberResponse> userClubsToUserClubMembersResponses(List<UserClub> userClubList, UserMapper userMapper, ImageMapper imageMapper) {
        if ( userClubList == null ) {
            return null;
        }

        List<UserClubDto.UserClubMemberResponse> list = new ArrayList<UserClubDto.UserClubMemberResponse>( userClubList.size() );
        for ( UserClub userClub : userClubList ) {
            list.add( userClubToUserClubMemberResponse(userClub, userMapper, imageMapper));
        }

        return list;
    }
}
