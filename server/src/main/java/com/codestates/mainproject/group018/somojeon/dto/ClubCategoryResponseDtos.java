package com.codestates.mainproject.group018.somojeon.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ClubCategoryResponseDtos<T> {
    private List<T> data;

    public ClubCategoryResponseDtos(List<T> data) {
        this.data = data;
    }
}
