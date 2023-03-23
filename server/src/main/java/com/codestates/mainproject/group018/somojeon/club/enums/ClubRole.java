package com.codestates.mainproject.group018.somojeon.club.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

public enum ClubRole {
    LEADER("LEADER"),
    MANAGER("MANAGER"),
    MEMBER("MEMBER");

    @Setter
    @Getter
    String roles;

    ClubRole(String roles) {
        this.roles = roles;
    }

    @JsonValue
    public String getRoles() {
        return roles;
    }
}
