package com.codestates.mainproject.group018.somojeon.group.enums;

import lombok.Getter;

public enum GroupMemberStatus {

    MEMBER_ACTIVE("MEMBER ACTIVE"),
    MEMBER_BLACKED("MEMBER BLACKED"),
    MEMBER_BANISHED("MEMBER BANISHED"),
    MEMBER_QUIT("MEMBER QUIT");

    @Getter
    String status;

    GroupMemberStatus(String status) {
        this.status = status;
    }
}
