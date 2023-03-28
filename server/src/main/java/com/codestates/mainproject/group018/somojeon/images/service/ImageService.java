package com.codestates.mainproject.group018.somojeon.images.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${defaultClub.image.address}")
    private String defaultClubImage;

    private final AmazonS3 amazonS3;


    public String uploadProfileImage(MultipartFile multipartFile) throws IOException {

        //중복 이미지 제목 피하기 위해 randomUUID 부여
        String fileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getInputStream().available());
        // 직접 다운로드가 아니고 브라우저에서 열수 있게 하는 코드
        objectMetadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, fileName, multipartFile.getInputStream(), objectMetadata);

        return amazonS3.getUrl(bucket, fileName).toString();
    }


    public String uploadClubImage(MultipartFile multipartFile) throws IOException {

        //중복 이미지 제목 피하기 위해 randomUUID 부여
        String fileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getInputStream().available());
        // 직접 다운로드가 아니고 브라우저에서 열수 있게 하는 코드
        objectMetadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, fileName, multipartFile.getInputStream(), objectMetadata);

        return amazonS3.getUrl(bucket, fileName).toString();
    }


    public String deleteImage(String url) {
        if (url != null) {
            String fileName = url.substring(url.lastIndexOf("/") + 1);
            amazonS3.deleteObject(bucket, fileName);
        }

        return "이미지 삭제됨";
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new RuntimeException("Wrong file extension(" + fileName + ")");
        }
    }
}
