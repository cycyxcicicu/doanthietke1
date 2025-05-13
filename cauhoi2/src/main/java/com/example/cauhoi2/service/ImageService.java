package com.example.cauhoi2.service;

import com.example.cauhoi2.client.ImageClient;
import com.example.cauhoi2.dto.response.ImageResponse;
import com.example.cauhoi2.dto.response.ImageUploadResponse;
import com.example.cauhoi2.entity.Image;
import com.example.cauhoi2.exception.AppException;
import com.example.cauhoi2.exception.ErrorCode;
import com.example.cauhoi2.mapper.ImageMapper;
import com.example.cauhoi2.repository.ImageRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ImageService {
    @Autowired
    ImageClient imageClient;
    @Value("${image-upload-service.client-id}")
    String userIdServiceImage;
    @Autowired
    ImageMapper imageMapper;
    @Autowired
    ImageRepository imageRepository;
    public String saveImageToLocal(byte[] imageBytes, String fileName) {
        try {
            Path uploadPath = Paths.get("uploads");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path imagePath = uploadPath.resolve(fileName);
            Files.write(imagePath, imageBytes);
            return "/uploads/" + fileName;
        } catch (IOException e) {
            throw new AppException(ErrorCode.CANNOT_SAVE_IMAGE);
        }
    }

    public Image saveImageToService(MultipartFile multipartFile) {
        ImageUploadResponse response = imageClient.uploadImage("Client-ID " + userIdServiceImage, multipartFile).getData();
        return imageRepository.save(imageMapper.toImage(response));
    }

    public boolean deleteImageFromService(String id) {
        Image image = imageRepository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.IMAGE_NOT_EXISTED));
        var response = imageClient.deleteImage("Client-ID " + userIdServiceImage, image.getDeletehash());
        //if (response.isSuccess()) {
        //    imageRepository.delete(image);
            return true;
        //}
        //return false;
    }

    public List<Image> getAllHashDelete() {
        return imageRepository.findAll();
    }
}
