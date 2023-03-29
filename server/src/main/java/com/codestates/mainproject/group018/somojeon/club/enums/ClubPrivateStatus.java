package com.codestates.mainproject.group018.somojeon.club.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum ClubPrivateStatus {
    PUBLIC("PUBLIC"),
    SECRET("SECRET");

    @Getter
    String status;

    ClubPrivateStatus(String status) {
        this.status = status;
    }

    @JsonValue
    public String getStatus() {
        return status;
    }
}
