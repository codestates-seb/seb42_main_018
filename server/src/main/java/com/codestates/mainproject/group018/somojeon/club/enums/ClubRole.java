package com.codestates.mainproject.group018.somojeon.club.enums;

import lombok.Getter;

public enum ClubRole {
    Leader("소모임장"),
    Manager("매니저"),
    Member("일반회원");

    @Getter
    String clubRole;

    ClubRole(String clubRole) {
        this.clubRole = clubRole;
    }
}
