package com.example.cauhoi2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cauhoi2.dto.request.ApiResponse;
import com.example.cauhoi2.dto.request.TestRequest;
import com.example.cauhoi2.dto.response.TestResponse;
import com.example.cauhoi2.service.ClassService;
import com.example.cauhoi2.service.TestService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/test")

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestController {
    @Autowired
     TestService testService;
     @PostMapping("/{classid}")
     public ApiResponse<TestResponse> createTest(@RequestBody TestRequest request,@PathVariable("classid") String id) {
         ApiResponse<TestResponse> apiResponse= new ApiResponse<>();
        //  apiResponse.setResult(testService.creaTest(request,id));
            testService.creaTest(request,id);
         apiResponse.setMessage("Tạo bài kiểm tra  thành công");
         return apiResponse;
     }

     @PutMapping("/{testid}")
     public ApiResponse<TestResponse> updatetest(@RequestBody TestRequest request,@PathVariable("testid") String id) {
        ApiResponse<TestResponse> apiResponse= new ApiResponse<>();
        testService.updatetest(request,id);
        // apiResponse.setResult(testService.updatetest(request,id));
        apiResponse.setMessage("Sửa thành công");
         return apiResponse;
     }
     @DeleteMapping
     public ApiResponse<TestResponse> deletetest(@PathVariable("testid") String id) {
        ApiResponse<TestResponse> apiResponse= new ApiResponse<>();
        apiResponse.setMessage(testService.deleteTest(id));
        return apiResponse;
     }
    @GetMapping("/{classid}")
    public ApiResponse<List<TestResponse>> gettestById(@PathVariable("classid") String id) {
        ApiResponse<List<TestResponse>> apiResponse= new ApiResponse<>();
        apiResponse.setResult(testService.getTestById(id));
        return apiResponse;
    }
    @GetMapping
    public ApiResponse<List<TestResponse>> getAlltest() {
        ApiResponse<List<TestResponse>> apiResponse= new ApiResponse<>();
        apiResponse.setResult(testService.getAllTests());
        return apiResponse;
    }
    

    

}
