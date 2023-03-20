package com.codestates.mainproject.group018.somojeon.images.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.codestates.mainproject.group018.somojeon.club.entity.Club;
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
import java.util.Optional;
import java.util.UUID;

@CrossOrigin
@Slf4j
@Service
public class ImageService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;
    private final ImagesRepository imagesRepository;

    public ImageService(AmazonS3 amazonS3, ImagesRepository imagesRepository) {
        this.amazonS3 = amazonS3;
        this.imagesRepository = imagesRepository;
    }

    public Images uploadProfileImage(MultipartFile multipartFile) throws IOException {
        //중복 이미지 제목 피하기 위해 randomUUID 부여
        String fileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getInputStream().available());

        amazonS3.putObject(bucket + "user", fileName, multipartFile.getInputStream(), objectMetadata);

        Images images = new Images();
        images.setFileName(fileName);
        images.setUrl("/user/" + fileName); //S3 저장 폴더 위치
        imagesRepository.save(images);

        log.info("프로필 이미지 파일 업로드됨");
        return imagesRepository.save(images);
    }

    public Images uploadClubImage(MultipartFile multipartFile) throws IOException {
        //중복 이미지 제목 피하기 위해 randomUUID 부여
        String fileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getInputStream().available());

        amazonS3.putObject(bucket + "club", fileName, multipartFile.getInputStream(), objectMetadata);

        Images images = new Images();
        images.setFileName(fileName);
        images.setUrl("/club/" + fileName); //S3 저장 폴더 위치
        imagesRepository.save(images);

        log.info("소모임 이미지 파일 업로드됨");
        return imagesRepository.save(images);
    }

    public String deleteProfileImage(String url) throws IOException {

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

    public String deleteClubImage(String url) throws IOException {

        Images images = findVerifiedUpFileUrl(url);
        Club club = new Club();
        String fileName = images.getFileName();
        try {
            club.setImages(images);
            imagesRepository.delete(images);
            amazonS3.deleteObject(new DeleteObjectRequest(bucket + "club", fileName));
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
        }
        return "클럽 이미지 파일 삭제됨";
    }

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
