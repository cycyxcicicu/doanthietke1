package com.example.cauhoi2.controller;

import com.example.cauhoi2.dto.response.ApiResponse;
import com.example.cauhoi2.service.MixService;
import com.example.cauhoi2.service.ReadFileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/read-file")
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ReadFileController {
    ReadFileService readFileService;
    MixService mixService;
    @PostMapping("/")
    public ApiResponse<?> readFile(@RequestParam("file") MultipartFile file) throws IOException {
        return ApiResponse.builder().code(1000).message("Success").result(readFileService.readFile(file)).build();
    }
}
