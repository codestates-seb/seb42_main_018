package com.codestates.mainproject.group018.somojeon.images.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import com.codestates.mainproject.group018.somojeon.club.repository.ClubRepository;
import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.images.entity.Images;
import com.codestates.mainproject.group018.somojeon.images.repository.ImagesRepository;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
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
    private final ImagesRepository imagesRepository;

    public String uploadDefaultImage() {
        Images images = new Images();
        images.setUrl(defaultClubImage);
        imagesRepository.save(images);
        return images.getUrl();

    }

    public Images uploadProfileImage(MultipartFile multipartFile) throws IOException {
        //중복 이미지 제목 피하기 위해 randomUUID 부여
        String fileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getInputStream().available());
        // 직접 다운로드가 아니고 브라우저에서 열수 있게 하는 코드
        objectMetadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(new PutObjectRequest(bucket + "/user", fileName, multipartFile.getInputStream(), objectMetadata));

        Images images = new Images();
        images.setFileName(fileName);
        images.setUrl(bucket + fileName);
        imagesRepository.save(images);

        log.info("프로필 이미지 파일 업로드됨");
        return imagesRepository.save(images);
    }

    public String uploadClubImage(MultipartFile multipartFile) throws IOException {

//        String fileName = UUID.randomUUID().toString().concat(getFileExtension(multipartFile.getOriginalFilename()));

        String fileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
        try {

            InputStream fileInputStream = multipartFile.getInputStream();
            ObjectMetadata objMeta = new ObjectMetadata();
            objMeta.setContentLength(fileInputStream.available());

            log.info("Store an image into the storage");
            amazonS3.putObject(bucket + "/club", fileName, fileInputStream, objMeta);
            fileInputStream.close();

        } catch (IOException e) {
            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED);
        }

        return amazonS3.getUrl(bucket, fileName).toString();

//        //중복 이미지 제목 피하기 위해 randomUUID 부여
//        String fileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
//
//        ObjectMetadata objectMetadata = new ObjectMetadata();
//        objectMetadata.setContentLength(multipartFile.getInputStream().available());
//        // 직접 다운로드가 아니고 브라우저에서 열수 있게 하는 코드
//        objectMetadata.setContentType(multipartFile.getContentType());
//
//        amazonS3.putObject(bucket + "/club", fileName, multipartFile.getInputStream(), objectMetadata);
//
//        Images images = new Images();
//        images.setFileName(fileName);
//        images.setUrl(bucket + fileName); //S3 저장 폴더 위치
//
//        imagesRepository.save(images);
//
//        log.info("소모임 이미지 파일 업로드됨");
//
//        return images.getUrl();
    }

    public void deleteClubImage(Images images) {
        String fileName = images.getFileName();
        amazonS3.deleteObject(bucket, fileName);
    }

    public String deleteProfileImage(String url)  {

        Images images = findVerifiedUpFileUrl(url);
        User user = images.getUser();
        String fileName = images.getFileName();
        try {
            user.setImages(null);
            imagesRepository.delete(images);
            amazonS3.deleteObject(new DeleteObjectRequest(bucket + "user", fileName));
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
        }
        return "프로필 이미지 파일 삭제됨";
    }

//    public String deleteClubImage(String url) {
//
//        Images images = findVerifiedUpFileUrl(url);
//        Club club = new Club();
//        String fileName = images.getFileName();
//        try {
//            club.setImages(images);
//            imagesRepository.delete(images);
//            amazonS3.deleteObject(new DeleteObjectRequest(bucket + "club", fileName));
//        } catch (AmazonServiceException e) {
//            System.err.println(e.getErrorMessage());
//        }
//        return "클럽 이미지 파일 삭제됨";
//    }

//    public String deleteClubImage(Images images) {
//
//        Images fileUrl = findVerifiedUpFileUrl(images.getUrl());
//
//        String fileName = images.getFileName();
//        try {
//            club.setImages(images);
//            imagesRepository.delete(images);
//            amazonS3.deleteObject(new DeleteObjectRequest(bucket + "club", fileName));
//        } catch (AmazonServiceException e) {
//            System.err.println(e.getErrorMessage());
//        }
//        return "클럽 이미지 파일 삭제됨";
//    }

    //존재하는 url인지 검증
    public Images findVerifiedUpFileUrl (String url){
        Optional<Images> optionalS3UpFile = imagesRepository.findByUrl(url);
        Images images =
                optionalS3UpFile.orElseThrow(() -> new BusinessLogicException(ExceptionCode.S3_FILE_NOT_FOUND));

        return images;
    }

    //존재하는 id 인지 검증
    public Images validateVerifyFile(Long imageId){
        Optional<Images> optionalS3UpFile = imagesRepository.findById(imageId);
        Images images = optionalS3UpFile.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.S3_FILE_NOT_FOUND));

        return images;
    }

}
