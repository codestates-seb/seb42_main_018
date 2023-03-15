package com.codestates.mainproject.group018.somojeon.club.enums;

import lombok.Getter;
import lombok.Setter;

public enum ClubMemberStatus {

    MEMBER_ACTIVE("회원 활동중"),
    MEMBER_BLACKED("회원 블랙상태"),
    MEMBER_BANISHED("회원 밴상태"),
    MEMBER_QUIT("회원 탈퇴상태");

    @Setter
    @Getter
    String status;

    ClubMemberStatus(String status) {
        this.status = status;
    }
}
