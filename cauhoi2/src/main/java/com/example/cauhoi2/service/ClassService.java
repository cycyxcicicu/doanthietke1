package com.example.cauhoi2.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.cauhoi2.entity.Clasz;
import org.springframework.stereotype.Service;

import com.example.cauhoi2.dto.request.ClassCreationRequest;
import com.example.cauhoi2.dto.request.ClassUpdateRequest;
import com.example.cauhoi2.dto.response.ClassResponse;
import com.example.cauhoi2.dto.response.UserOfClassResponse;
import com.example.cauhoi2.repository.ClassRepository;
import com.example.cauhoi2.repository.UserClassRoleRepository;
import com.example.cauhoi2.repository.UserRepository;
import com.example.cauhoi2.entity.User;
import com.example.cauhoi2.entity.UserClassRole;
import com.example.cauhoi2.exception.AppException;
import com.example.cauhoi2.exception.ErrorCode;
import com.example.cauhoi2.mapper.ClassMapper;
import com.example.cauhoi2.mapper.UserMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClassService {
    ClassRepository classRepository;
    UserClassRoleRepository userClassRoleRepository;
    UserRepository userRepository;
    ClassMapper classMapper;
    UserMapper userMapper;
    public ClassResponse createClass( ClassCreationRequest request, String id){

        Clasz clasz = classMapper.toClass(request);
        UserClassRole userClassRole = new UserClassRole();

        userClassRole.setUser(userRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED)));
        clasz=classRepository.save(clasz);
        userClassRole.setClasz(clasz);
        userClassRole.setRole(UserClassRole.Role.TEACHER);

        userClassRoleRepository.save(userClassRole);

        return classMapper.toClassResponse(clasz);
    }
    public List<ClassResponse> getclass(){

        return classMapper.toListClassResponse(classRepository.findAll());
    }
   public List<ClassResponse> getclassbyid(String userid) {
    // Lấy danh sách Class_id
    List<String> classIds = userClassRoleRepository.findByIdAndDeleteNotTrue(userid);

    // Lấy các lớp từ Class_id
    List<Clasz> claszList = classRepository.findClassesByClassIds(classIds);

    // Lấy các roles từ UserClassRole cho userId
    List<UserClassRole> userClassRoles = userClassRoleRepository.findByUserId(userid);

    // Lấy số lượng thành viên cho mỗi lớp (dựa vào classId)
    Map<String, Long> classMemberCount = new HashMap<>();
    for (String classId : classIds) {
        // Lấy userIds của sinh viên không bị xóa cho lớp cụ thể
        List<String> userIds = userClassRoleRepository.findByUserStudentAndNotDelete(classId);
        classMemberCount.put(classId, (long) userIds.size());  // Đếm số lượng thành viên cho lớp
    }

    // Ánh xạ Clasz và role vào ClassResponse
    List<ClassResponse> classResponses = claszList.stream()
        .map(clasz -> {
            // Lấy role từ UserClassRole
            String role = userClassRoles.stream()
                .filter(userClassRole -> userClassRole.getClasz().getClassId().equals(clasz.getClassId()))
                .map(UserClassRole::getRole)
                .findFirst()
                .orElse(UserClassRole.Role.STUDENT) // Default Role if not found
                .name();

            // Lấy số lượng thành viên từ classMemberCount Map
            Long memberCount = classMemberCount.getOrDefault(clasz.getClassId(), 0L);

            return ClassResponse.builder()
                .classid(clasz.getClassId())
                .name(clasz.getName())
                .imageclass(clasz.getImageClass())
                .role(role)
                .isdelete(clasz.getIsDelete() != null ? clasz.getIsDelete() : false)
                .soluongthanhvien(memberCount.toString()) // Thêm số lượng thành viên vào ClassResponse
                .build();
        })
        .collect(Collectors.toList());

    return classResponses;
}


    public ClassResponse updateClass(ClassUpdateRequest request, String id){
            Clasz clas= classRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.CLASS_NOT_EXISTED));
            Boolean is= clas.getIsDelete();
            classMapper.toUpdateClass(clas,request);
            clas.setIsDelete(is);
            return classMapper.toClassResponse(classRepository.save(clas));

    }
    public String themthanhvienclass(String classid, String Userid){
        UserClassRole userClassRole = new UserClassRole();
        if (userClassRoleRepository.findByClassAndUser(classid,Userid).isEmpty())
            {userClassRole.setUser(userRepository.findById(Userid).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED)));
            userClassRole.setClasz(classRepository.findById(classid).orElseThrow(()-> new AppException(ErrorCode.CLASS_NOT_EXISTED)));}
        else{
            return "thanh vien nay da ton tai";
        }
        userClassRole.setRole(UserClassRole.Role.STUDENT);
        userClassRoleRepository.save(userClassRole);
        return "Thêm thành viên thành công";
    }
    public String deleteclass(String id){
        Clasz clas= classRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.CLASS_NOT_EXISTED));
        clas.setIsDelete(true);
        clas=classRepository.save(clas);
        List<UserClassRole> userClassRoles=userClassRoleRepository.findByClassId(clas.getClassId());

        for (UserClassRole userClassRole : userClassRoles) {
            userClassRole.setIsDelete(true);  // Cập nhật cột delete
        }
        userClassRoleRepository.saveAll(userClassRoles);

        return "xóa lớp thanh công";
    }
    public String deletethanhvien(String classid, String userid){
        
        UserClassRole userClassRole = userClassRoleRepository.findByClassAndUser(classid,userid).orElseThrow(() -> new AppException(ErrorCode.CLASS_USER_NOT_EXISTED));
        userClassRoleRepository.delete(userClassRole);

        return "xóa thành công";
    }
    public List<UserOfClassResponse> getclassbyuser(String Classid){
        List<String> userid = userClassRoleRepository.findByUserStudentAndNotDelete(Classid);
        List<User> users = userRepository.findAllById(userid);
        
        List<UserOfClassResponse> userofClassResponses = new ArrayList<>();
        for (User user : users){
            UserOfClassResponse response = userMapper.toUserofClassResponse(user);
            userofClassResponses.add(response);
        }
        return userofClassResponses;

    }

}
