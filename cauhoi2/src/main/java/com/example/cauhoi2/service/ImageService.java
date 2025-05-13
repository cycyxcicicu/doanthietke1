package com.example.cauhoi2.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.cauhoi2.client.ImageUploadClient;
import com.example.cauhoi2.dto.response.ImageResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageService {
    ImageUploadClient imageUploadClient;
    String clientId = "546c25a59c58ad7";
    public ImageResponse saveImage(MultipartFile file) {
        return imageUploadClient.uploadImage(clientId, file);
    }
}
