package com.example.cauhoi2.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.example.cauhoi2.dto.response.ImageResponse;

@FeignClient(name = "image-upload", url = "${external.image.api}")
public interface ImageUploadClient {
     @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ImageResponse uploadImage(
        @RequestParam("client_id") String token,
        @RequestPart("image") MultipartFile file
    );
}
