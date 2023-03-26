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

    default UserClubDto.JoinResponse userClubToJoinResponse(UserClub userClub) {

        if (userClub == null) {
            return null;
        }

        UserClubDto.JoinResponse joinResponse = new UserClubDto.JoinResponse();
        joinResponse.setUserClubId(userClub.getUserClubId());
        joinResponse.setContent(userClub.getContent());
        joinResponse.setJoinStatus(userClub.getJoinStatus());
        joinResponse.setClubRole(userClub.getClubRole());
        joinResponse.setUserInfo(userToUserInfoResponse(userClub.getUser()));

        return joinResponse;
    }

    default List<UserClubDto.JoinResponse> userClubsToJoinResponses(List<UserClub> userClubList) {
        if ( userClubList == null ) {
            return null;
        }

        List<UserClubDto.JoinResponse> list = new ArrayList<UserClubDto.JoinResponse>( userClubList.size() );
        for ( UserClub userClub : userClubList ) {
            list.add( userClubToJoinResponse( userClub ) );
        }

        return list;
    }

    default UserDto.UserInfoResponse userToUserInfoResponse(User user) {

        UserDto.UserInfoResponse userInfoResponse = new UserDto.UserInfoResponse();
        userInfoResponse.setUserId(user.getUserId());
        userInfoResponse.setEmail(user.getEmail());
        userInfoResponse.setNickName(user.getNickName());
        userInfoResponse.setProfileImage(user.getProfileImageUrl());

        return userInfoResponse;
    }

    default ImagesResponseDto imagesToImagesResponseDto(Images images) {
        if ( images == null ) {
            return null;
        }

        ImagesResponseDto response = new ImagesResponseDto();

        response.setImageId(images.getImageId());
        response.setFileName(images.getFileName());
        response.setUrl(images.getUrl());

        return response;
    }




    default UserClubDto.Response userClubToUserClubResponse(UserClub userClub) {
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
        response.userInfo(userToUserInfoResponse(userClub.getUser()));

        return response.build();
    }

    default List<UserClubDto.Response> userClubsToUserCLubResponses(List<UserClub> userClubs){
        return userClubs.stream()
                .map(this::userClubToUserClubResponse)
                .collect(Collectors.toList());
    };


    default UserClubDto.UserClubMemberResponse userClubToUserClubMemberResponse(UserClub userClub) {
        if (userClub == null) {
            return null;
        }

        UserClubDto.UserClubMemberResponse userClubMemberResponse = new UserClubDto.UserClubMemberResponse();

        userClubMemberResponse.setUserClubId(userClub.getUserClubId());
        userClubMemberResponse.setClubMemberStatus(userClub.getClubMemberStatus());
        userClubMemberResponse.setUserInfo(userToUserInfoResponse(userClub.getUser()));

        return userClubMemberResponse;
    }
    default List<UserClubDto.UserClubMemberResponse> userClubsToUserClubMembersResponses(List<UserClub> userClubList) {
        if ( userClubList == null ) {
            return null;
        }

        List<UserClubDto.UserClubMemberResponse> list = new ArrayList<UserClubDto.UserClubMemberResponse>( userClubList.size() );
        for ( UserClub userClub : userClubList ) {
            list.add( userClubToUserClubMemberResponse(userClub));
        }

        return list;
    }
}
