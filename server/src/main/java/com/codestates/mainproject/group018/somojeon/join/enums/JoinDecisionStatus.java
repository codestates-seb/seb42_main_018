package com.codestates.mainproject.group018.somojeon.join.enums;

import lombok.Getter;

public enum JoinDecisionStatus {
    PENDING("가입 신청 중"),
    CONFIRMED("가입 승인"),
    REFUSED("가입 거절"),
    BANISHED("클럽에서 차단");

    @Getter
    String status;

    JoinDecisionStatus(String status) {
        this.status = status;
    }
}
