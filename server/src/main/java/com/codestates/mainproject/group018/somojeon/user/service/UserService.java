package com.codestates.mainproject.group018.somojeon.user.service;

import com.codestates.mainproject.group018.somojeon.auth.token.JwtTokenProvider;
import com.codestates.mainproject.group018.somojeon.auth.token.JwtTokenizer;
import com.codestates.mainproject.group018.somojeon.auth.utils.CustomAuthorityUtils;
import com.codestates.mainproject.group018.somojeon.club.entity.UserClub;
import com.codestates.mainproject.group018.somojeon.club.service.ClubService;
import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.images.service.ImageService;
import com.codestates.mainproject.group018.somojeon.oauth.service.OauthUserService;
import com.codestates.mainproject.group018.somojeon.user.dto.UserDto;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import com.codestates.mainproject.group018.somojeon.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;
    private final JwtTokenizer jwtTokenizer;

    @Value("${defaultProfile.image.address}")
    private String defaultProfileImage;
    private final ClubService clubService;
    private final ImageService imageService;
    private final OauthUserService oauthUserService;


    public User createUser(User user, String token, HttpServletResponse response) {
        verifyExistsEmail(user.getEmail());

        // DB에 User Role 저장
        List<String> roles = authorityUtils.createRoles(user.getEmail());
        user.setRoles(roles);

        if(token != null){
            user.setPassword("OAUTH2.0");
            oauthUserService.createOAuthUser(token, user);
        }
        // DB에 password 암호화해서 저장
        if(user.getPassword() == null) throw new BusinessLogicException(ExceptionCode.WRONG_REQUEST);
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        // DB에 User Role 저장
        user.setProfileImageUrl(defaultProfileImage);

        User savedUser = userRepository.save(user);

        return savedUser;
    }


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public User updateUser(Long userId, String nickName, MultipartFile multipartFile) throws IOException {
        User findUser = findVerifiedUser(userId);

        if (multipartFile == null) {
            findUser.setNickName(nickName);
        } else {
            findUser.setNickName(nickName);
            findUser.setProfileImageUrl(imageService.uploadProfileImage(multipartFile));
        }

        return userRepository.save(findUser);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public User updateUserPassword(UserDto.PatchPassword userDto, HttpServletResponse response)  {
        User findUser = findVerifiedUser(userDto.getUserId());
        String savedPassword = findUser.getPassword();
        String currentPassword =  userDto.getCurrentPassword();
        if(!passwordEncoder.matches(currentPassword, savedPassword)){
            response.addHeader("request", "False");
            throw new BusinessLogicException(ExceptionCode.CURRENT_PASSWORD_NOT_MATCH);
        }

        String nextPassword = userDto.getNextPassword();
        String nextPasswordCheck = userDto.getNextPasswordCheck();

        if(!nextPassword.equals(nextPasswordCheck)){
            response.addHeader("request", "False");
            throw new BusinessLogicException(ExceptionCode.NEXT_PASSWORD_NOT_MATCH);
        }
        findUser.setPassword(passwordEncoder.encode(nextPassword));
        return userRepository.save(findUser);
    }


    @Transactional(readOnly = true)
    public User findUser(long userId) {
        User findUser = findVerifiedUser(userId);


        return findUser;

    }

    @Transactional(readOnly = true)
    public  List<UserClub> findUserClub(Long userId) {
        List<UserClub> userClubs =  clubService.getUserClubs(userId);

        return userClubs;

    }


    public void deleteUser(long userId) {
        User findUser = findVerifiedUser(userId);

        userRepository.delete(findUser);
    }

    @Transactional(readOnly = true)
    public User findVerifiedUser(long userId) {
        Optional<User> optionalUser =
                userRepository.findById(userId);
        User findUser =
                optionalUser.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        return findUser;
    }

    @Transactional(readOnly = true)
    public User findUserByEmail(String email){
        Optional<User> optionalUser =
                userRepository.findByEmail(email);
        User findUser =
                optionalUser.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        return findUser;
    }

    public void verifyExistsEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            throw new BusinessLogicException(ExceptionCode.USER_EXISTS);
        }
    }

    public void verifyExistsEmail(String email, HttpServletResponse response) {
        Optional<User> user = userRepository.findByEmail(email);
        String answer = user.isPresent() ? "True" : "False";
        response.addHeader("Request", answer);
    }

    public boolean verifyMyUserId(HttpServletRequest request, Long userId){
        return jwtTokenizer.getUserId(request.getHeader("Authorization")) == userId;
    }

    // 로그인 한 사람의 이메일 가져오기
    private String findLoginUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    // 로그인 유저 얻기
    public User getLoginUser() {
        Optional<User> optionalUser = userRepository.findByEmail(findLoginUserEmail());
        User user = optionalUser.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        return user;
    }


    public User changeUserState(long userId, String state) {
        User user = findVerifiedUser(userId);
        List<String> roles = authorityUtils.createRoles(user.getEmail());
        switch (state){
            case "NEW":
                user.setUserStatus(User.UserStatus.USER_NEW);
                break;
            case "ACTIVE":
                user.setUserStatus(User.UserStatus.USER_ACTIVE);
                break;
            case "SLEEP":
                user.setUserStatus(User.UserStatus.USER_SLEEP);
                break;
            case "QUIT":
                user.setUserStatus(User.UserStatus.USER_QUIT);
                break;
            case "BLOCK":
                user.setUserStatus(User.UserStatus.USER_BLOCK);
                roles = null;
                break;
        }
        user.setRoles(new ArrayList<>(roles));
        user = userRepository.save(user);

        return user;
    }
}