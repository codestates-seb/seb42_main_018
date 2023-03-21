package com.codestates.mainproject.group018.somojeon.images.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImagesResponseDto {
    private Long imageId;
    private String fileName;
    private String url;
}
