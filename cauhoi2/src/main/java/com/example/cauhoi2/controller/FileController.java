package com.example.cauhoi2.controller;

import com.example.cauhoi2.dto.request.ExportFileRequest;
import com.example.cauhoi2.dto.response.ApiResponse;
import com.example.cauhoi2.dto.response.ExamResponse;
import com.example.cauhoi2.service.ExamService;
import com.example.cauhoi2.service.ExportFileService;
import com.example.cauhoi2.service.MixService;
import com.example.cauhoi2.service.ReadFileService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/file")
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FileController {
    ReadFileService readFileService;
    MixService mixService;
    ExamService examService;
    ExportFileService exportFileService;
    @PostMapping("/read")
    public ApiResponse<?> readFile(@RequestParam("file") MultipartFile file) throws IOException {
        return ApiResponse.builder().code(1000).message("Success").result(readFileService.readFile(file)).build();
    }

    @PostMapping("/export")
    public ResponseEntity<byte[]> exportExamToWord(@RequestBody @Valid ExportFileRequest request) {
        ExamResponse exam = examService.getById(request.getExamId());
        List<ExamResponse> exams = mixService.mix(exam, request.getCount());
        byte[] zipBytes = exportFileService.exportManyExamsToZip(request.getName(), exams);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"de-thi.zip\"")
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(zipBytes);
    }

}
