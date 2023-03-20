package com.codestates.mainproject.group018.somojeon.images.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ImagesResponseDto {
    private Long imageId;
    private String fileName;
    private String url;
}
