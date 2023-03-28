package com.codestates.mainproject.group018.somojeon.user.mapper;


import com.codestates.mainproject.group018.somojeon.club.entity.UserClub;
import com.codestates.mainproject.group018.somojeon.club.mapper.ClubMapper;
import com.codestates.mainproject.group018.somojeon.user.dto.UserDto;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {ClubMapper.class})
public interface UserMapper {
    User userPostToUser(UserDto.Post userDtoPost);
    User userPatchToUser(UserDto.Patch userDtoPatch);

    default UserDto.Response userToUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto.Response response = new UserDto.Response();

        response.setUserId( user.getUserId() );
        response.setNickName( user.getNickName() );
        response.setEmail( user.getEmail() );
        response.setUserStatus( user.getUserStatus() );
        response.setProfileImage(user.getProfileImageUrl());

        return response;
    }

    default  List<UserDto.Response> usersToUserResponses(List<User> users){
        return users.stream().map(
                user -> userToUserResponse(user)
        ).collect(Collectors.toList());
    };


    default UserDto.ResponseWithClubs userToUserResponseWithClubs(User user, List<UserClub> userClubs
            , ClubMapper clubMapper){
        UserDto.ResponseWithClubs responseWithClubs = new UserDto.ResponseWithClubs(
                user.getUserId(),
                user.getNickName(),
                user.getEmail(),
                user.getUserStatus(),
                user.getProfileImageUrl(),
                clubMapper.userClubsToUserCLubResponses(userClubs)
        );
        return responseWithClubs;
    }

    default UserDto.ResponseWithClub userToUserResponseWithClub(UserClub userClub){
        User user = userClub.getUser();
        UserDto.ResponseWithClub responseWithClub = new UserDto.ResponseWithClub();
        responseWithClub.setUserId(user.getUserId());
        responseWithClub.setNickName(user.getNickName());
        responseWithClub.setClubMemberStatus(userClub.getClubMemberStatus());
        responseWithClub.setClubRole(userClub.getClubRole());
        responseWithClub.setProfileImage(user.getProfileImageUrl());
        responseWithClub.setPlayCount(userClub.getPlayCount());
        responseWithClub.setWinCount(userClub.getWinCount());
        responseWithClub.setLoseCount(userClub.getLoseCount());
        responseWithClub.setDrawCount(userClub.getDrawCount());
        responseWithClub.setWinRate(userClub.getWinRate());
        return responseWithClub;
    }
}
