package com.codestates.mainproject.group018.somojeon.User.mapper;


import com.codestates.mainproject.group018.somojeon.User.Entity.User;
import com.codestates.mainproject.group018.somojeon.User.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "Spring")
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
        response.setName(user.getUserName());
        response.setEmail(user.getEmail());
        response.setUserStatus(user.getUserStatus());
        response.setUserId(user.getUserId());

        // todo 멤버 설정에 따라 변환가능
        response.setAge(-1);

        return response;

    };
}
