package com.codestates.mainproject.group018.somojeon.exception;

import lombok.Getter;

// ??
public enum ExceptionCode {
    USER_NOT_FOUND(404, "User not found"),
    MEMBER_NOT_FOUND(404, "Member not found"),
    CLIENT_NOT_FOUND(404, "Client not found"),
    USER_EXISTS(409, "User exists"),
    CURRENT_PASSWORD_NOT_MATCH(409, "Current password is not match"),
    NEXT_PASSWORD_NOT_MATCH(409, "Next password is not match"),
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
    USER_CLUB_NOT_FOUND(404, "User club not found"),
    CLUB_MEMBER_EXISTS(409, "User club exists"),
    JOIN_EXISTS(409, "You already applied for a join"),
    S3_FILE_NOT_FOUND(404, "S3 file not found"),
    ACCESS_DENIED_JOIN_THIS_CLUB(401, "You can't join this club"),
    EMAIL_ALREADY_EXIT(1000, "Same email already exits"),
    CLUB_SAVE_ERROR(400, "Club data error, please try again"),
    TEAM_SAVE_ERROR(400, "Team data error, please try again"),
    CANDIDATE_SAVE_ERROR(400, "Candidate data error, please try again"),
    RECORD_SAVE_ERROR(400, "Record data error, please try again"),
    GENERAL_ERROR(400, "Data error, please try again");





    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
