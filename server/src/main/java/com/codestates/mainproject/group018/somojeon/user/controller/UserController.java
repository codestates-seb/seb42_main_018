package com.codestates.mainproject.group018.somojeon.user.controller;

import com.codestates.mainproject.group018.somojeon.auth.token.JwtTokenProvider;
import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import com.codestates.mainproject.group018.somojeon.club.entity.UserClub;
import com.codestates.mainproject.group018.somojeon.club.mapper.ClubMapper;
import com.codestates.mainproject.group018.somojeon.club.service.ClubService;
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
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {
    private final static String USER_DEFAULT_URL = "/users";
    private final UserService userService;
    private final UserMapper userMapper;
    private final Identifier identifier;
    private final ClubService clubService;

    private final ClubMapper clubMapper;
    private final JwtTokenProvider jwtTokenProvider;


    // post
    @PostMapping()
    public ResponseEntity postUser(@Valid @RequestBody UserDto.Post userDtoPost,
                                   HttpServletRequest request, HttpServletResponse response) {
        User user = userMapper.userPostToUser(userDtoPost);
        String token = identifier.getAccessToken(request);
        User createdUser = userService.createUser(user, token, response);
        jwtTokenProvider.provideTokens(createdUser, response);
        List<UserClub> userClubs = userService.findUserClub(createdUser.getUserId());

        return new ResponseEntity<>(
                new SingleResponseDto<>(userMapper.userToUserResponseWithClubs(createdUser, userClubs, clubMapper)),
                HttpStatus.OK);
    }

    @PostMapping("/email")
    public ResponseEntity checkUserEmail(@Valid @RequestBody UserDto.Post userDtoPost,
                                         HttpServletResponse response) {
        User user = userMapper.userPostToUser(userDtoPost);
        userService.verifyExistsEmail(user.getEmail(), response);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 이전 코드는 주석처리 해놓
//    @PatchMapping("/{user-id}")
//    public ResponseEntity patchUser(
//            @PathVariable("user-id") @Positive long userId,
//            @Valid @RequestBody UserDto.Patch requestBody) {
//
//        requestBody.setUserId(userId);
//        if(!identifier.isVerified(userId)){
//            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED_PATCH_USER);
//        }
//        User user = userService.updateUser(userMapper.userPatchToUser(requestBody));
//
//        return new ResponseEntity<>(
//                new SingleResponseDto<>(userMapper.userToUserResponse(user)),
//                HttpStatus.OK);
//    }

    @PatchMapping("/{user-id}")
    public ResponseEntity patchUser(@PathVariable("user-id") @Positive Long userId,
                                    @RequestParam String nickName,
                                    @RequestParam(value = "profileImage") MultipartFile multipartFile) throws IOException {

        if (!identifier.isVerified(userId)) {
            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED_PATCH_USER);
        }
        User response = userService.updateUser(userId, nickName, multipartFile);

        return new ResponseEntity<>(
                new SingleResponseDto<>(userMapper.userToUserResponse(response)), HttpStatus.OK);
    }

    @PatchMapping("/{user-id}/password")
    public ResponseEntity patchUserPassword(
            @PathVariable("user-id") @Positive long userId,
            @Valid @RequestBody UserDto.PatchPassword requestBody) {

        requestBody.setUserId(userId);
        if (!identifier.isVerified(userId)) {
            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED_PATCH_USER);
        }
        User user = userService.updateUserPassword(requestBody);

        return new ResponseEntity<>(
                new SingleResponseDto<>(userMapper.userToUserResponse(user)),
                HttpStatus.OK);

    }


    // get
    @GetMapping("/{user-id}")
    public ResponseEntity getUser(@PathVariable("user-id") @Positive long userId,
                                  HttpServletRequest request) {

        User findUser = userService.findUser(userId);
        List<UserClub> userClubs = userService.findUserClub(userId);
        UserDto.ResponseWithClubs response = userMapper.userToUserResponseWithClubs(findUser, userClubs, clubMapper);

        return new ResponseEntity<>(
                new SingleResponseDto<>(response), HttpStatus.OK);

    }

    // UserClub으로 옮김

//    @GetMapping("/clubs/{club-id}")
//    public ResponseEntity getClubUsers(@RequestParam @Positive int page,
//                                     @RequestParam @Positive int size,
//                                       @PathVariable("club-id") @Positive Long clubId){
//        if(!identifier.isAdmin() && !identifier.getClubIds().contains(clubId)){
//            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED);
//        }
//
//        Page<UserClub> pageUserClubs = userService.findUsers(page-1, size, clubId);
//        List<UserClub> userClubs = pageUserClubs.getContent();
//
//
//        List<UserDto.ResponseWithClub> response =  userClubs.stream().map(
//                userClub -> userMapper.userToUserResponseWithClub(userClub, imageMapper)
//        ).collect(Collectors.toList());
//
//
//        return new ResponseEntity<>(new MultiResponseDto<>(response, pageUserClubs),
//                HttpStatus.OK);
//    }

    // delete
    @DeleteMapping("/{user-id}")
    public ResponseEntity deleteUser(@PathVariable("user-id") @Positive long userId,
                                     HttpServletRequest request) {
        if (!identifier.isVerified(userId)) {
            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED);
        }
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/admin/{user-id}")
    public ResponseEntity patchUserState(@PathVariable("user-id") @Positive long userId,
                                         @RequestParam String state) {

        if (!identifier.isAdmin()) {
            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED);
        }

        if (identifier.isAdmin(userId) && state.equals("BLOCK"))
            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED);
        User user = userService.changeUserState(userId, state);

        return new ResponseEntity<>(
                new SingleResponseDto<>(userMapper.userToUserResponse(user)),
                HttpStatus.OK);

    }

    // 내가 가입한 소모임 조회
    @GetMapping("/{user-id}/clubs")
    public ResponseEntity getMyClubs(@PathVariable("user-id") @Positive Long userId,
                                     @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "100") int size) {

        if (!identifier.isVerified(userId) && identifier.isAdmin()) {
            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED);
        }

        Page<Club> myClubs = clubService.findMyClubs(page - 1, size, userId);
        List<Club> content = myClubs.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(
                        clubMapper.clubToClubResponseDtos(content), myClubs), HttpStatus.OK);
    }

    // 내가 가입신청한 소모임 조회
    @GetMapping("/{user-id}/joins")
    public ResponseEntity getClubsByMyJoinRequest(@PathVariable("user-id") @Positive Long userId,
                                           @RequestParam(defaultValue = "1") int page,
                                           @RequestParam(defaultValue = "100") int size) {

        if (!identifier.isVerified(userId) && !identifier.isAdmin()) {
            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED);
        }

        Page<Club> joinPage = clubService.findClubsByMyJoinRequest(page - 1, size, userId);
        List<Club> content = joinPage.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(
                        clubMapper.clubToClubResponseDtos(content), joinPage), HttpStatus.OK);
    }
}
