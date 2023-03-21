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

    UserDto.Response userToUserResponse(User user);

    List<UserDto.Response> usersToUserResponses(List<User> users);


    default UserDto.ResponseWithClubs userToUserResponseWithClub(User user, List<UserClub> userClubs, ClubMapper clubMapper){
        UserDto.ResponseWithClubs responseWithClubs = new UserDto.ResponseWithClubs(
                user.getUserId(),
                user.getNickName(),
                user.getEmail(),
                user.getUserStatus(),
                clubMapper.userClubsToUserCLubResponses(userClubs)
        );
        return responseWithClubs;
    }

//    default List<UserDto.Response> usersToUserResponses(List<User> users, List<List<UserClub>> userClubsList, ClubMapper clubMapper){
//        ArrayList<UserDto.Response> userResponse = new ArrayList<>();
//        for(int i = 0 ; i < users.size(); i++){
//            userResponse.add(this.userToUserResponse(users.get(i), userClubsList.get(i), clubMapper));
//        }
//        return userResponse;
//
//    }

    default List<UserDto.Response> usersToUserResponsesForPublic(List<User> users){
        return users.stream()
                .map(this::userToUserResponseForPublic)
                .collect(Collectors.toList());
    }

    default UserDto.Response userToUserResponseForPublic(User user){
        UserDto.Response response = new UserDto.Response();
        response.setNickName(user.getNickName());
        response.setEmail(user.getEmail());
        response.setUserStatus(user.getUserStatus());
        response.setUserId(user.getUserId());

        return response;

    };
}
