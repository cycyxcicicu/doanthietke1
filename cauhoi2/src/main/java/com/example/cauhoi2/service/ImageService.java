package com.example.cauhoi2.service;

import com.example.cauhoi2.entity.Image;
import com.example.cauhoi2.exception.AppException;
import com.example.cauhoi2.exception.ErrorCode;
import com.example.cauhoi2.mapper.ImageMapper;
import com.example.cauhoi2.repository.ImageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ImageService {
    @NonFinal
    @Value("${image-upload-service.client-id}")
    String userIdServiceImage;
    ImageMapper imageMapper;
    ImageRepository imageRepository;
    public Image saveImageToLocal(byte[] imageBytes, String fileName, int stt) {
        try {
            Path uploadPath = Paths.get("uploads/images");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path imagePath = uploadPath.resolve(fileName);
            Files.write(imagePath, imageBytes);
            String link = "/uploads/images/" + fileName;
            return imageRepository.save(Image.builder().link(link).stt(stt).build());
        } catch (IOException e) {
            throw new AppException(ErrorCode.CANNOT_SAVE_IMAGE);
        }
    }

    public void deleteImagesFromService(List<Image> images) {
        String uploadDir = System.getProperty("user.dir") + "/uploads";

        for (Image image : images) {
            if (imageRepository.existsById(image.getId())) {
                try {
                    String fileName = image.getLink();

                    Path filePath = Paths.get(uploadDir, fileName);

                    // Kiểm tra file có tồn tại không
                    if (Files.exists(filePath)) {
                        Files.delete(filePath);
                        log.info("Đã xóa file: ", image.getLink());
                    } else {
                        log.info("File không tồn tại: ", image.getLink());
                    }

                    imageRepository.delete(image);
                } catch (IOException e) {
                    throw new AppException(ErrorCode.IMAGE_CANNOT_REMOVE);
                }
            }
        }
    }

    public List<Image> getAll() {
        return imageRepository.findAll();
    }
}
