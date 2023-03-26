package com.codestates.mainproject.group018.somojeon.club.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

public enum ClubMemberStatus {

    MEMBER_ACTIVE("MEMBER ACTIVE"),
    MEMBER_BLACKED("MEMBER BLACKED"),
    MEMBER_QUIT("MEMBER QUIT");

    @Setter
    @Getter
    String status;

    ClubMemberStatus(String status) {
        this.status = status;
    }

    @JsonValue
    public String getStatus() {
        return status;
    }
}
