package com.example.cauhoi2.service;

import com.example.cauhoi2.dto.request.CourseCreateRequest;
import com.example.cauhoi2.dto.response.CourseResponse;
import com.example.cauhoi2.entity.Course;
import com.example.cauhoi2.entity.User;
import com.example.cauhoi2.exception.AppException;
import com.example.cauhoi2.exception.ErrorCode;
import com.example.cauhoi2.mapper.CourseMapper;
import com.example.cauhoi2.repository.CourseRepository;
import com.example.cauhoi2.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CourseService {
    CourseRepository courseRepository;
    UserRepository userRepository;
    CourseMapper courseMapper;
    public List<CourseResponse> getCourses() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        var list = courseRepository.findAllByUserId(user.getId()).stream().toList();
        return courseMapper.toListCoursesResponse(list);
    }
    public CourseResponse getCourse(String id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        var course = courseRepository
            .findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));
        if (!course.getUser().getId().equals(user.getId()))
            throw new AppException(ErrorCode.UNAUTHORIZED);
        return courseMapper.toCourseResponse(course);
    }
    public CourseResponse create(CourseCreateRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        try {
            var countCourse = courseRepository.findAllByUserId(user.getId()).stream().toList().size();
            Course newCourse = courseMapper.toCourse(request);
            newCourse.setUser(user);
            newCourse.setStt(countCourse + 1);
            newCourse = courseRepository.save(newCourse);
            return courseMapper.toCourseResponse(newCourse);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.COURSE_EXISTED);
        }
    }
}
