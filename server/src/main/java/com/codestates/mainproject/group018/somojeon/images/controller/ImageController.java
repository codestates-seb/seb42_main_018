package com.codestates.mainproject.group018.somojeon.images.controller;

import com.codestates.mainproject.group018.somojeon.club.service.ClubService;
import com.codestates.mainproject.group018.somojeon.images.dto.ImagesResponseDto;
import com.codestates.mainproject.group018.somojeon.images.entity.Images;
import com.codestates.mainproject.group018.somojeon.images.mapper.ImageMapper;
import com.codestates.mainproject.group018.somojeon.images.service.ImageService;
import com.codestates.mainproject.group018.somojeon.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/upload")
public class ImageController {

    private final ImageService imageService;
    private final ImageMapper mapper;

    // 유저 프로필 이미지 파일 업로드
    @PostMapping(value = "/users", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadProfileImage(@RequestPart(value = "image", required = false) MultipartFile multipartFile) throws IOException {

        Images images = imageService.uploadProfileImage(multipartFile);
        ImagesResponseDto response = mapper.imagesToImageResponseDto(images);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    // 클럽 소개 이미지 파일 업로드
//    @PostMapping(value = "/clubs", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity uploadClubImage(@RequestPart(value = "image", required = false) MultipartFile multipartFile) throws IOException{
//
//        Images images = imageService.uploadClubImage(multipartFile);
//        ImagesResponseDto response = mapper.imagesToImageResponseDto(images);
//
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }

    // 유저 프로필 이미지 파일 삭제
    @DeleteMapping("/users")
    public ResponseEntity deleteProfileImage(@RequestPart("url") String url) {

        imageService.deleteProfileImage(url);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

//    // 클럽 소개 이미지 파일 삭제
//    @DeleteMapping("/clubs")
//    public ResponseEntity deleteClubImage(@RequestPart("url") String url) {
//
//        imageService.deleteClubImage(url);
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }
}
