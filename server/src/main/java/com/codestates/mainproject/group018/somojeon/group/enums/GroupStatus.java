package com.codestates.mainproject.group018.somojeon.group.enums;

import lombok.Getter;

public enum GroupStatus {

    GROUP_ACTIVE("GROUP ACTIVE"),
    FREEZE_A_GROUP("FREEZE A GROUP");

    @Getter
    String status;

    GroupStatus(String status) {
        this.status = status;
    }
}
