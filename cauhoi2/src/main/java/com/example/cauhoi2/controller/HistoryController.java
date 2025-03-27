package com.example.cauhoi2.controller;

import com.example.cauhoi2.dto.request.ApiResponse;
import com.example.cauhoi2.dto.request.HistoryRequest;
import com.example.cauhoi2.dto.response.HistoryResponse;
import com.example.cauhoi2.service.HistoryService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/history")
@FieldDefaults(level = AccessLevel.PRIVATE)

public class HistoryController {
    @Autowired
    HistoryService historyService;

    @PostMapping()
    public ApiResponse<List <HistoryResponse>> postMethodName(@RequestBody List<HistoryRequest>  request) {
        ApiResponse<List<HistoryResponse>> apiResponse= new ApiResponse<>();
        apiResponse.setMessage("Nộp bài thành công");
        historyService.createHistory(request);
        return apiResponse;
    }
    @GetMapping("/{testid}/{userid}")
    public ApiResponse<List <HistoryResponse>> getMethodName(@PathVariable("testid") String testid, @PathVariable("userid") String userid) {
        ApiResponse<List <HistoryResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(historyService.gethistory(testid,userid));
        return apiResponse;
    }
}
