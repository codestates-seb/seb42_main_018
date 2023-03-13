package com.codestates.mainproject.group018.somojeon.images.controller;

import com.codestates.mainproject.group018.somojeon.dto.BaseResponse;
import com.codestates.mainproject.group018.somojeon.images.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class FileUploadController {
    private S3Service s3Service;

    @PostMapping
    public ResponseEntity<BaseResponse> upload(@RequestPart MultipartFile file) {
        String url = s3Service.upload(file);
        return ResponseEntity.ok(BaseResponse.builder().message("이미지 업로드 성공").data(url).build());
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse> delete(@RequestParam String fileName) {
        s3Service.delete(fileName);
        return ResponseEntity.ok(BaseResponse.builder().message("이미지 삭제 성공").build());
    }
}
