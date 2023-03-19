package com.codestates.mainproject.group018.somojeon.images.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ImagesDto {
    private String title;
    private String url;
    private MultipartFile file;
}
