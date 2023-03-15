package com.codestates.mainproject.group018.somojeon.user.service;

import com.codestates.mainproject.group018.somojeon.auth.service.AuthService;
import com.codestates.mainproject.group018.somojeon.auth.token.JwtTokenizer;
import com.codestates.mainproject.group018.somojeon.auth.utils.CustomAuthorityUtils;
import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.oauth.service.OauthUserService;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import com.codestates.mainproject.group018.somojeon.user.repository.UserRepository;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Transactional
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;
    private final JwtTokenizer jwtTokenizer;
    private final AuthService authService;
    private final OauthUserService oauthUserService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, CustomAuthorityUtils authorityUtils
            , JwtTokenizer jwtTokenizer, AuthService authService, OauthUserService oauthUserService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityUtils = authorityUtils;
        this.jwtTokenizer = jwtTokenizer;
        this.authService = authService;
        this.oauthUserService = oauthUserService;
    }

    public User createUser(User user, HttpServletRequest request) {
        verifyExistsEmail(user.getEmail());
        if(IsOAuthSignUp(request)){
            user.setPassword("OAUTH2.0");
            oauthUserService.createOAuthUser(request, user);
        }
        // DB에 password 암호화해서 저장
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        // DB에 User Role 저장
        List<String> roles = authorityUtils.createRoles(user.getEmail());
        user.setRoles(roles);


        User savedUser = userRepository.save(user);

        return savedUser;
    }

    private boolean IsOAuthSignUp(HttpServletRequest request) {
        String token = request.getHeader("Access");
        if(token == null) return false;
        Map<String, Object> claims = authService.getClaimsValues(token);
        String registration =  (String) claims.get("registration");
        String registrationId =  (String) claims.get("registrationId");
        if(registration == null || registrationId == null) return false;
        return true;

    }



    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public User updateUser(User user) throws IllegalAccessException {
        User findUser = findVerifiedUser(user.getUserId());
        // 널이 아닌 값을 복사한다.

        // TODO 로직 수정 필요
//        Optional<String> optionalUserName = Optional.ofNullable(user.getUserName());
//        optionalUserName.ifPresent(
//                name -> findUser.setUserName(name)
//        );
//
//        Optional<Integer> optionalAge = Optional.ofNullable(user.getAge());
//        optionalAge.ifPresent(
//                age -> findUser.setAge(age)
//        );

        return userRepository.save(findUser);
    }

    @Transactional(readOnly = true)
    public User findUser(long userId) {
        User findUser = findVerifiedUser(userId);
        return findUser;

    }

    public Page<User> findUsers(int page, int size, String  mode) {
        // TODO 로직 요구 조건에 맞춰 수정
        String property = "userId";
//        if (mode == "vote") property = "voteCount";

        return userRepository.findAll(PageRequest.of(page, size,
                Sort.by(property).descending()));
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

    private void verifyExistsEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent())
            throw new BusinessLogicException(ExceptionCode.USER_EXISTS);
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


}