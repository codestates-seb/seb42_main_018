package com.codestates.mainproject.group018.somojeon.join.enums;

import lombok.Getter;

public enum JoinDecisionStatus {
    CONFIRMED("가입 승인"),
    REFUSED("가입 거절");

    @Getter
    String status;

    JoinDecisionStatus(String status) {
        this.status = status;
    }
}
