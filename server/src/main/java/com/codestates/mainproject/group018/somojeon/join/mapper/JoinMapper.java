package com.codestates.mainproject.group018.somojeon.join.mapper;

import com.codestates.mainproject.group018.somojeon.club.entity.ClubTag;
import com.codestates.mainproject.group018.somojeon.join.dto.JoinDto;
import com.codestates.mainproject.group018.somojeon.join.entity.Joins;
import com.codestates.mainproject.group018.somojeon.tag.dto.TagDto;
import com.codestates.mainproject.group018.somojeon.user.dto.UserDto;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JoinMapper {

    Joins joinPostDtoToJoins(JoinDto.Post requestBody);

    Joins joinDecisionPatchDtoToJoins(JoinDto.DecisionPatch requestBody);

//    JoinDto.Response joinsToJoinResponseDto(Joins joins);

    JoinDto.DecisionResponse joinsToJoinDecisionResponseDto(Joins joins);

    default JoinDto.Response joinsToJoinResponseDto(Joins joins) {
        if ( joins == null ) {
            return null;
        }

        return JoinDto.Response
                .builder()
                .userInfo(userToUserResponse(joins.getUser()))
                .content(joins.getContent())
                .build();
    }

    List<JoinDto.Response> JoinsToJoinResponseDtos(List<Joins> joinsList);

    default UserDto.Response userToUserResponse(User user) {

        UserDto.Response userInfo = new UserDto.Response();
        userInfo.setUserId(user.getUserId());
        userInfo.setNickName(user.getNickName());
        userInfo.setUserStatus(user.getUserStatus());
        userInfo.setEmail(user.getEmail());

        return userInfo;
    }

}
