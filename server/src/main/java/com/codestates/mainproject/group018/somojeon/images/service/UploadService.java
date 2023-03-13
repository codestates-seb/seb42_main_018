package com.codestates.mainproject.group018.somojeon.images.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    String upload(MultipartFile multipartFile);
    void delete(String fileName);
}
