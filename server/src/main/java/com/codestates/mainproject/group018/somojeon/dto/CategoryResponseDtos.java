package com.codestates.mainproject.group018.somojeon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class CategoryResponseDtos<T> {
    private List<T> data;

    public CategoryResponseDtos(List<T> data) {
        this.data = data;
    }
}
