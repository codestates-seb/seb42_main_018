package com.codestates.mainproject.group018.somojeon.group.enums;

import lombok.Getter;

public enum ClubStatus {

    CLUB_ACTIVE("CLUB ACTIVE"),
    FREEZE_A_CLUB("FREEZE A CLUB");

    @Getter
    String status;

    ClubStatus(String status) {
        this.status = status;
    }
}
