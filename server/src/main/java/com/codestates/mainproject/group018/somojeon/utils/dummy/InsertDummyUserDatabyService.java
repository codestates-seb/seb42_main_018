package com.codestates.mainproject.group018.somojeon.utils.dummy;

import com.codestates.mainproject.group018.somojeon.user.entity.User;
import com.codestates.mainproject.group018.somojeon.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Profile("dummy-insert") // dummy` 프로파일이 활성화 될 경우에만 실행된다.
@Component
class InsertDummyUserDatabyService {
    @Autowired
    UserService userService;

    @PostConstruct
    public void init() {
        log.info("# Run ProfileTestComponent by a dummy profile!");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        for (int i =  1; i <= 100; i++) {
            String email = "user" + i + "@dummy.com";
            String nickName = "nick" + i;
            String encodedPassword = "password" + i;
            User user = new User();
            user.setPassword(encodedPassword);
            user.setNickName(nickName);
            user.setEmail(email);
            userService.createUser(user, null);
        }
        System.out.println("100 rows inserted successfully.");
    }
}

