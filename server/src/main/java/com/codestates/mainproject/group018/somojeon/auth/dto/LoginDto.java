package com.codestates.mainproject.group018.somojeon.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class LoginDto {
    private String email;
    private String password;
}
