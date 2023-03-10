package com.codestates.mainproject.group018.somojeon.exception;

import lombok.Getter;

// ??
public enum ExceptionCode {
    USER_NOT_FOUND(404, "User not found"),
    USER_EXISTS(409, "User exists"),
    CATEGORY_EXISTS(409, "Category exists"),
    CLUB_EXISTS(409, "Club exists"),
    NOT_IMPLEMENTATION(501, "Not Implementation"),
    CATEGORY_NOT_FOUND(404, "Category not found"),
    CLUB_NOT_FOUND(404, "Club not found"),
    JOIN_NOT_FOUND(404, "Join not found"),
    INVALID_MEMBER_STATUS(400, "Invalid member status"),
    REQUEST_FORBIDDEN(403,"Request forbidden"),
    MEMBER_UNAUTHORIZED(401, "UNAUTHORIZED"),
    ACCESS_DENIED(401, "Access Denied");




    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
