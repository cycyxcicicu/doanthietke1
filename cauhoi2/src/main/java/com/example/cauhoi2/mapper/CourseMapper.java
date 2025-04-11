package com.example.cauhoi2.mapper;

import com.example.cauhoi2.dto.request.CourseCreateRequest;
import com.example.cauhoi2.dto.response.CourseResponse;
import com.example.cauhoi2.entity.Course;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseResponse toCourseResponse(Course course);
    List<CourseResponse> toListCoursesResponse(List<Course> courses);
    Course toCourse(CourseCreateRequest courseCreateRequest);
}
