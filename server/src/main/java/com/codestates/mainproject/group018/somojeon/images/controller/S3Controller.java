package com.codestates.mainproject.group018.somojeon.images.controller;

import com.codestates.mainproject.group018.somojeon.images.dto.ImagesDto;
import com.codestates.mainproject.group018.somojeon.images.entity.Images;
import com.codestates.mainproject.group018.somojeon.images.service.ImagesService;
import com.codestates.mainproject.group018.somojeon.images.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class S3Controller {
    private final S3Service s3Service;
    private final ImagesService imagesService;

    @GetMapping("/api/upload")
    public String goToUpload() {
        return "upload";
    }

    @PostMapping("/api/upload")
    public String uploadFile(ImagesDto imagesDto) throws IOException {
        String url = s3Service.uploadFile(imagesDto.getFile());

        imagesDto.setUrl(url);
        imagesService.save(imagesDto);

        return "redirect:/api/list";
    }

    @GetMapping("/api/list")
    public String listPage(Model model) {
        List<Images> imagesList =imagesService.getFiles();
        model.addAttribute("fileList", imagesList);
        return "list";
    }
}
