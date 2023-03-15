package com.codestates.mainproject.group018.somojeon.club.enums;

import lombok.Getter;
import lombok.Setter;

public enum ClubStatus {

    CLUB_ACTIVE("소모임 활동중"),
    FREEZE_A_CLUB("소모임 이용정지");

    @Setter
    @Getter
    String status;

    ClubStatus(String status) {
        this.status = status;
    }
}
