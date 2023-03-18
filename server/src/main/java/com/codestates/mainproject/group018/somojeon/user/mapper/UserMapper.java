package com.codestates.mainproject.group018.somojeon.user.mapper;


import com.codestates.mainproject.group018.somojeon.user.dto.UserDto;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User userPostToUser(UserDto.Post userDtoPost);
    User userPatchToUser(UserDto.Patch userDtoPatch);


    @Named("private")
    UserDto.Response userToUserResponse(User user);

    default List<UserDto.Response> usersToUserResponses(List<User> users){
        return users.stream()
                .map(this::userToUserResponse)
                .collect(Collectors.toList());
    }

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
