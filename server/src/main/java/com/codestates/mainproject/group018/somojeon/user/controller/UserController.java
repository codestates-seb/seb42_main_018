package com.codestates.mainproject.group018.somojeon.user.controller;

import com.codestates.mainproject.group018.somojeon.user.entity.User;
import com.codestates.mainproject.group018.somojeon.user.dto.UserDto;
import com.codestates.mainproject.group018.somojeon.user.mapper.UserMapper;
import com.codestates.mainproject.group018.somojeon.user.service.UserService;
import com.codestates.mainproject.group018.somojeon.dto.SingleResponseDto;
import com.codestates.mainproject.group018.somojeon.utils.UriCreator;
import lombok.extern.slf4j.Slf4j;
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

    public UserController(UserService userService, UserMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }


    // post
    @PostMapping()
    public ResponseEntity postUser(@Valid @RequestBody UserDto.Post userDtoPost,
                                   HttpServletRequest request){
        User user =  mapper.userPostToUser(userDtoPost);
        User createdUser =  userService.createUser(user, request);
        URI location = UriCreator.createUri(USER_DEFAULT_URL, createdUser.getUserId());
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{user-id}")
    public ResponseEntity patchUser(
            @PathVariable("user-id") @Positive long userId,
            @Valid @RequestBody UserDto.Patch requestBody,
            HttpServletRequest request) throws IllegalAccessException {

        requestBody.setUserId(userId);
//        if(!(userService.verifyMyUserId(request, userId)  || Checker.checkAdmin())){
//            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED);
//        }

//        User user = userService.updateUser(mapper.userPatchToUser(requestBody));

//        return new ResponseEntity<>(
//                new SingleResponseDto<>(mapper.userToUserResponse(user)),
//                HttpStatus.OK);

        return null;
    }

    // get
    @GetMapping("/{user-id}")
    public ResponseEntity getUser(@PathVariable("user-id") @Positive long userId,
                                    HttpServletRequest request){

//        User findUser =
//                userService.findUser(userId);
//
//        UserDto.Response  response = (userService.verifyMyUserId(request, userId) || Checker.checkAdmin()) ?
//                mapper.userToUserResponse(findUser):
//                mapper.userToUserResponseForPublic(findUser);

        // TODO MOCKING
        UserDto.Response response = new UserDto.Response(
                1L,
                "mockUser",
                "mock@email.com",
                20,
                User.UserStatus.USER_ACTIVE);

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

    // delete
    @DeleteMapping("/{user-id}")
    public ResponseEntity deleteUser(@PathVariable("user-id") @Positive long userId,
                                       HttpServletRequest request){
//        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
