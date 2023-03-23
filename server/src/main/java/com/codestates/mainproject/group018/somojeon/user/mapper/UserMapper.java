package com.codestates.mainproject.group018.somojeon.user.mapper;


import com.codestates.mainproject.group018.somojeon.club.entity.UserClub;
import com.codestates.mainproject.group018.somojeon.club.mapper.ClubMapper;
import com.codestates.mainproject.group018.somojeon.images.dto.ImagesResponseDto;
import com.codestates.mainproject.group018.somojeon.images.entity.Images;
import com.codestates.mainproject.group018.somojeon.images.mapper.ImageMapper;
import com.codestates.mainproject.group018.somojeon.user.dto.UserDto;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {ClubMapper.class, ImageMapper.class})
public interface UserMapper {
    User userPostToUser(UserDto.Post userDtoPost);
    User userPatchToUser(UserDto.Patch userDtoPatch);

    UserDto.Response userToUserResponse(User user);

    default  List<UserDto.Response> usersToUserResponses(List<User> users){
        return users.stream().map(
                user -> userToUserResponse(user)
        ).collect(Collectors.toList());
    };


    default UserDto.ResponseWithClubs userToUserResponseWithClubs(User user, List<UserClub> userClubs
            , ClubMapper clubMapper, ImageMapper imageMapper){
        UserDto.ResponseWithClubs responseWithClubs = new UserDto.ResponseWithClubs(
                user.getUserId(),
                user.getNickName(),
                user.getEmail(),
                user.getUserStatus(),
                imageMapper.imagesToImageResponseDto(user.getImages()),
                clubMapper.userClubsToUserCLubResponses(userClubs)
        );
        return responseWithClubs;
    }

    default UserDto.ResponseWithClub userToUserResponseWithClub(UserClub userClub, ImageMapper imageMapper){
        User user = userClub.getUser();
        UserDto.ResponseWithClub responseWithClub = new UserDto.ResponseWithClub();
        responseWithClub.setNickName(user.getNickName());
        responseWithClub.setProfileImage(imageMapper.imagesToImageResponseDto(user.getImages()));
        responseWithClub.setPlayCount(userClub.getPlayCount());
        responseWithClub.setWinCount(userClub.getWinCount());
        responseWithClub.setLoseCount(userClub.getLoseCount());
        responseWithClub.setDrawCount(userClub.getDrawCount());
        responseWithClub.setWinRate(userClub.getWinRate());
        return responseWithClub;
    }

    default UserDto.UserClubResponse userToUserClubResponse(User user, ImageMapper imageMapper) {

        UserDto.UserClubResponse userClubResponse = new UserDto.UserClubResponse();
        userClubResponse.setUserId(user.getUserId());
        userClubResponse.setEmail(user.getEmail());
        userClubResponse.setNickName(user.getNickName());
        imageMapper.imagesToImageResponseDto(user.getImages());

        return userClubResponse;
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

}
