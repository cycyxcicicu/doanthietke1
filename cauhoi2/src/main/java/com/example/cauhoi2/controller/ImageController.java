package com.example.cauhoi2.controller;

import com.example.cauhoi2.dto.response.ApiResponse;
import com.example.cauhoi2.entity.Image;
import com.example.cauhoi2.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageController {
    @Autowired
    ImageService imageService;
    @PostMapping("/delete-all")
    ApiResponse<?> deleteImages() {
        for (Image image : imageService.getAllHashDelete())
            imageService.deleteImageFromService(image.getId());

        return ApiResponse.builder().code(1000).build();
    }
}
