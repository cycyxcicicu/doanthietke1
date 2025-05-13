package com.example.cauhoi2.client;


import com.example.cauhoi2.dto.request.ImageUploadRequest;
import com.example.cauhoi2.dto.response.ImageServiceApiResponse;
import com.example.cauhoi2.dto.response.ImageUploadResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "image-upload-service", url = "${image-upload-service.url}")
public interface ImageClient {

    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ImageServiceApiResponse<ImageUploadResponse> uploadImage(
            @RequestHeader("Authorization") String header,
            @RequestPart("image") MultipartFile image
    );

    @DeleteMapping(value = "/image/{hashdelete}", produces = MediaType.APPLICATION_JSON_VALUE)
    ImageServiceApiResponse<Boolean> deleteImage(
            @RequestHeader("Authorization") String header,
            @PathVariable("hashdelete") String hashdelete
    );
}