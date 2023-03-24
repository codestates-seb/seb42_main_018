package com.codestates.mainproject.group018.somojeon.club.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

public enum JoinStatus {
    PENDING("PENDING"),
    CONFIRMED("CONFIRMED"),
    REFUSED("REFUSED"),
    BANISHED("BANISHED");

    @Setter
    @Getter
    String status;

    JoinStatus(String status) {
        this.status = status;
    }

    @JsonValue
    public String getStatus() {
        return status;
    }
}
