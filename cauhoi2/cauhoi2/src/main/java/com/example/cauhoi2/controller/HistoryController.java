package com.example.cauhoi2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cauhoi2.dto.request.ApiResponse;
import com.example.cauhoi2.dto.request.HistoryRequest;
import com.example.cauhoi2.dto.response.HistoryReponse;
import com.example.cauhoi2.service.HistoryService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/history")
@FieldDefaults(level = AccessLevel.PRIVATE)

public class HistoryController {
    @Autowired
    HistoryService historyService;

    @PostMapping()
    public ApiResponse<List <HistoryReponse>> postMethodName(@RequestBody List<HistoryRequest>  request) {
        ApiResponse<List<HistoryReponse>> apiResponse= new ApiResponse<>();
        apiResponse.setMessage("Nộp bài thành công");
        historyService.createHistory(request);

        return apiResponse;
    }
    @GetMapping("/{testid}/{userid}")
    public ApiResponse<List <HistoryReponse>> getMethodName(@PathVariable("testid") String testid, @PathVariable("userid") String userid) {
        ApiResponse<List <HistoryReponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(historyService.gethistory(testid,userid));
        return apiResponse;
    }
    
    
}
