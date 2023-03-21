package com.codestates.mainproject.group018.somojeon.user.controller;

import com.codestates.mainproject.group018.somojeon.dto.SingleResponseDto;
import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.images.dto.ImagesResponseDto;
import com.codestates.mainproject.group018.somojeon.user.dto.UserDto;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import com.codestates.mainproject.group018.somojeon.user.mapper.UserMapper;
import com.codestates.mainproject.group018.somojeon.user.service.UserService;
import com.codestates.mainproject.group018.somojeon.utils.Identifier;
import com.codestates.mainproject.group018.somojeon.utils.UriCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;

@RestController
@RequestMapping("/users")
@Slf4j
@Validated
public class UserController {
    private final static String USER_DEFAULT_URL = "/users";
    private final UserService userService;
    private final UserMapper mapper;
    private final Identifier identifier;

    @Value("${defaultProfile.image.address}")
    private String defaultProfileImage;

    public UserController(UserService userService, UserMapper mapper, Identifier identifier) {
        this.userService = userService;
        this.mapper = mapper;
        this.identifier = identifier;
    }

    // post
    @PostMapping()
    public ResponseEntity postUser(@Valid @RequestBody UserDto.Post userDtoPost,
                                   HttpServletRequest request){
        Long profileImageId = userDtoPost.getProfileImageId();
        User user =  mapper.userPostToUser(userDtoPost);
        String token = identifier.getAccessToken(request);
        User createdUser =  userService.createUser(user, token, profileImageId);
        URI location = UriCreator.createUri(USER_DEFAULT_URL, createdUser.getUserId());
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{user-id}")
    public ResponseEntity patchUser(
            @PathVariable("user-id") @Positive long userId,
            @Valid @RequestBody UserDto.Patch requestBody) {

        Long profileImageId = requestBody.getProfileImageId();
        requestBody.setUserId(userId);
        if(!identifier.isVerified(userId)){
            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED_PATCH_USER);
        }
        User user = userService.updateUser(mapper.userPatchToUser(requestBody), profileImageId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.userToUserResponse(user)),
                HttpStatus.OK);

    }

    // get
    @GetMapping("/{user-id}")
    public ResponseEntity getUser(@PathVariable("user-id") @Positive long userId,
                                    HttpServletRequest request){

        User findUser =
                userService.findUser(userId);

        UserDto.Response  response =mapper.userToUserResponse(findUser);

        response.setUserId(userId);

        return  new ResponseEntity<>(
                new SingleResponseDto<>(response), HttpStatus.OK);

    }


    @GetMapping()
    public ResponseEntity getUsers(@RequestParam @Positive int page,
                                     @RequestParam @Positive int size,
                                     @RequestParam(required = false, defaultValue = "base") String mode,
                                     HttpServletRequest request){
//        Page<User> pageUsers = userService.findUsers(page-1, size, mode);
//        List<User> users = pageUsers.getContent();
//        List<UserDto.Response>  response = Checker.checkAdmin() ?
//                mapper.usersToUserResponses(users):
//                mapper.usersToUserResponsesForPublic(users);


//        return new ResponseEntity<>(new MultiResponseDto<>(response, pageUsers),
//                HttpStatus.OK);
        return null;
    }

    @GetMapping("/profile/{user-id}")
    public ResponseEntity<ImagesResponseDto> getProfile(@PathVariable("user-id") @Positive long userId) {
        User user = userService.findById(userId);
        if (user == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        ImagesResponseDto response = new ImagesResponseDto();
        if (user.getImages() != null) response.setUrl(response.getUrl());
        else response.setUrl(defaultProfileImage);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/{user-id}")
    public ResponseEntity deleteUser(@PathVariable("user-id") @Positive long userId,
                                       HttpServletRequest request){
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
