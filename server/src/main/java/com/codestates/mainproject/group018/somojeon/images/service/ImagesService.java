package com.codestates.mainproject.group018.somojeon.images.service;

import com.codestates.mainproject.group018.somojeon.images.dto.ImagesDto;
import com.codestates.mainproject.group018.somojeon.images.entity.Images;
import com.codestates.mainproject.group018.somojeon.images.repository.ImagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImagesService {
    private ImagesRepository imagesRepository;

    public void save(ImagesDto imagesDto) {
        Images images = new Images(imagesDto.getUrl());
        imagesRepository.save(images);
    }

    public List<Images> getFiles() {
        List<Images> all = imagesRepository.findAll();
        return all;
    }
}
