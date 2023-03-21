package com.codestates.mainproject.group018.somojeon.user.controller;

import com.codestates.mainproject.group018.somojeon.club.entity.UserClub;
import com.codestates.mainproject.group018.somojeon.club.mapper.ClubMapper;
import com.codestates.mainproject.group018.somojeon.dto.MultiResponseDto;
import com.codestates.mainproject.group018.somojeon.dto.SingleResponseDto;
import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.user.dto.UserDto;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import com.codestates.mainproject.group018.somojeon.user.mapper.UserMapper;
import com.codestates.mainproject.group018.somojeon.user.service.UserService;
import com.codestates.mainproject.group018.somojeon.utils.Identifier;
import com.codestates.mainproject.group018.somojeon.utils.UriCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Slf4j
@Validated
public class UserController {
    private final static String USER_DEFAULT_URL = "/users";
    private final UserService userService;
    private final UserMapper userMapper;
    private final Identifier identifier;

    private final ClubMapper clubMapper;

    public UserController(UserService userService, UserMapper userMapper,
                          Identifier identifier, ClubMapper clubMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.identifier = identifier;
        this.clubMapper = clubMapper;
    }

    // post
    @PostMapping()
    public ResponseEntity postUser(@Valid @RequestBody UserDto.Post userDtoPost,
                                   HttpServletRequest request){
        User user =  userMapper.userPostToUser(userDtoPost);
        String token = identifier.getAccessToken(request);
        User createdUser =  userService.createUser(user, token);
        URI location = UriCreator.createUri(USER_DEFAULT_URL, createdUser.getUserId());
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/email")
    public ResponseEntity checkUserEmail(@Valid @RequestBody UserDto.Post userDtoPost,
                                   HttpServletRequest request){
        User user =  userMapper.userPostToUser(userDtoPost);
        try{
            userService.verifyExistsEmail(user.getEmail());
        }
        catch (BusinessLogicException exception){
            return ResponseEntity.status(409).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{user-id}")
    public ResponseEntity patchUser(
            @PathVariable("user-id") @Positive long userId,
            @Valid @RequestBody UserDto.Patch requestBody) {

        requestBody.setUserId(userId);
        if(!identifier.isVerified(userId)){
            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED_PATCH_USER);
        }
        User user = userService.updateUser(userMapper.userPatchToUser(requestBody));

        return new ResponseEntity<>(
                new SingleResponseDto<>(userMapper.userToUserResponse(user)),
                HttpStatus.OK);

    }

    // get
    @GetMapping("/{user-id}")
    public ResponseEntity getUser(@PathVariable("user-id") @Positive long userId,
                                    HttpServletRequest request){

        User findUser =
                userService.findUser(userId);

        List<UserClub> userClubs = userService.findUserClub(userId);

        UserDto.ResponseWithClubs response = userMapper.userToUserResponseWithClubs(findUser, userClubs, clubMapper);

        return  new ResponseEntity<>(
                new SingleResponseDto<>(response), HttpStatus.OK);

    }


    @GetMapping("/{club-id}")
    public ResponseEntity getClubUsers(@RequestParam @Positive int page,
                                     @RequestParam @Positive int size,
                                       @PathVariable("club-id") @Positive long clubId,
                                     HttpServletRequest request){
        Page<UserClub> pageUserClubs = userService.findUsers(page-1, size, clubId);
        List<UserClub> userClubs = pageUserClubs.getContent();


        List<UserDto.ResponseWithClub> response =  userClubs.stream().map(
                userClub -> userMapper.userToUserResponseWithClub(userClub)
        ).collect(Collectors.toList());


        return new ResponseEntity<>(new MultiResponseDto<>(response, pageUserClubs),
                HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/{user-id}")
    public ResponseEntity deleteUser(@PathVariable("user-id") @Positive long userId,
                                       HttpServletRequest request){
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
