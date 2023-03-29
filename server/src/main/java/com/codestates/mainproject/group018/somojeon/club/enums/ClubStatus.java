package com.codestates.mainproject.group018.somojeon.club.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

public enum ClubStatus {

    CLUB_ACTIVE("CLUB ACTIVE"),
    FREEZE_A_CLUB("CLUB FREEZE");

    @Setter
    @Getter
    String status;

    ClubStatus(String status) {
        this.status = status;
    }

    @JsonValue
    public String getStatus() {
        return status;
    }
}
