package com.codestates.mainproject.group018.somojeon.club.enums;

import lombok.Getter;
import lombok.Setter;

public enum ClubRole {
    Leader("소모임장"),
    Manager("매니저"),
    Member("일반회원");

    @Setter
    @Getter
    String roles;

    ClubRole(String roles) {
        this.roles = roles;
    }
}
