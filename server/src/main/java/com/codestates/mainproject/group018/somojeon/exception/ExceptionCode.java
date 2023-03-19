package com.codestates.mainproject.group018.somojeon.exception;

import lombok.Getter;

// ??
public enum ExceptionCode {
    USER_NOT_FOUND(404, "User not found"),
    CLIENT_NOT_FOUND(404, "Client not found"),
    USER_EXISTS(409, "User exists"),
    CATEGORY_EXISTS(409, "Category exists"),
    CLUB_EXISTS(409, "Club exists"),
    NOT_IMPLEMENTATION(501, "Not Implementation"),
    CATEGORY_NOT_FOUND(404, "Category not found"),
    CLUB_NOT_FOUND(404, "Club not found"),
    JOIN_NOT_FOUND(404, "Join not found"),
    INVALID_MEMBER_STATUS(400, "Invalid member status"),
    TAG_CAN_NOT_OVER_THREE(400,"Tag can not over three(3)"),
    CLUB_CAN_NOT_DELETE(400, "Club can not delete"),
    WRONG_REQUEST(400, "wrong request"),
    REQUEST_FORBIDDEN(403,"Request forbidden"),
    MEMBER_UNAUTHORIZED(401, "UNAUTHORIZED"),
    ACCESS_DENIED(401, "Access Denied"),
    ACCESS_DENIED_PATCH_USER(401, "Can't change other's information"),
    COMMENT_NOT_FOUND(404, "Comment not found"),
    RECORD_NOT_FOUND(404, "Record not found"),
    NOT_FOUND_EXCEPTION(404, "Not found"),
    SCHEDULE_NOT_FOUND(404, "Schedule not found"),
    TEAM_NOT_FOUND(404, "Team not found"),
    //TODO - DW 검토부탁드려요
    USER_CLUB_NOT_FOUND(404, "User_club not found");





    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
