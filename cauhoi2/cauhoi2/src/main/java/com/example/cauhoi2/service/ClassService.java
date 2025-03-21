package com.example.cauhoi2.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.support.BeanDefinitionDsl.Role;
import org.springframework.stereotype.Service;

import com.example.cauhoi2.dto.request.ClassCreationRequest;
import com.example.cauhoi2.dto.request.ClassUpdateRequest;
import com.example.cauhoi2.dto.response.ClassResponse;
import com.example.cauhoi2.dto.response.UserofClassResponse;
import com.example.cauhoi2.repository.ClaszRepository;
import com.example.cauhoi2.repository.UserClassRoleRepostiory;
import com.example.cauhoi2.repository.UserRepository;
import com.example.cauhoi2.entity.Clasz;
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
    ClaszRepository claszRepository;
    UserClassRoleRepostiory userClassRolerRepostiory;
    UserRepository userRepository;
    ClassMapper classMapper;
    UserMapper userMapper;
    public ClassResponse createclass( ClassCreationRequest request, String id){

        Clasz clasz = classMapper.toClass(request);
        UserClassRole userClassRole = new UserClassRole();

        userClassRole.setUser(userRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED)));
        clasz=claszRepository.save(clasz);
        userClassRole.setClasz(clasz);
        userClassRole.setRole(UserClassRole.Role.TEACHER);

        userClassRolerRepostiory.save(userClassRole);

        return classMapper.toClassResponse(clasz);
    }
    public List<ClassResponse> getclass(){

        return classMapper.toListClassResponse(claszRepository.findAll());
    }
   public List<ClassResponse> getclassbyid(String userid) {
    // Lấy danh sách Class_id
    List<String> classIds = userClassRolerRepostiory.findByIdAndDeleteNotTrue(userid);

    // Lấy các lớp từ Class_id
    List<Clasz> claszList = claszRepository.findClassesByClassIds(classIds);

    // Lấy các roles từ UserClassRole cho userId
    List<UserClassRole> userClassRoles = userClassRolerRepostiory.findByUser_Userid(userid);

    // Lấy số lượng thành viên cho mỗi lớp (dựa vào classId)
    Map<String, Long> classMemberCount = new HashMap<>();
    for (String classId : classIds) {
        // Lấy userIds của sinh viên không bị xóa cho lớp cụ thể
        List<String> userIds = userClassRolerRepostiory.findByUserStudentandnotdelete(classId);
        classMemberCount.put(classId, (long) userIds.size());  // Đếm số lượng thành viên cho lớp
    }

    // Ánh xạ Clasz và role vào ClassResponse
    List<ClassResponse> classResponses = claszList.stream()
        .map(clasz -> {
            // Lấy role từ UserClassRole
            String role = userClassRoles.stream()
                .filter(userClassRole -> userClassRole.getClasz().getClassid().equals(clasz.getClassid()))
                .map(UserClassRole::getRole)
                .findFirst()
                .orElse(UserClassRole.Role.STUDENT) // Default Role if not found
                .name();

            // Lấy số lượng thành viên từ classMemberCount Map
            Long memberCount = classMemberCount.getOrDefault(clasz.getClassid(), 0L);

            return ClassResponse.builder()
                .classid(clasz.getClassid())
                .name(clasz.getName())
                .imageclass(clasz.getImageclass())
                .role(role)
                .isdelete(clasz.getIsdelete() != null ? clasz.getIsdelete() : false)
                .soluongthanhvien(memberCount.toString()) // Thêm số lượng thành viên vào ClassResponse
                .build();
        })
        .collect(Collectors.toList());

    return classResponses;
}


    public ClassResponse updateClass(ClassUpdateRequest request, String id){
            Clasz clas= claszRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.CLASS_NOT_EXISTED));
            Boolean is= clas.getIsdelete();
            classMapper.toupdateClass(clas,request);
            clas.setIsdelete(is);
            return classMapper.toClassResponse(claszRepository.save(clas));

    }
    public String themthanhvienclass(String classid, String Userid){
        UserClassRole userClassRole = new UserClassRole();
        if (userClassRolerRepostiory.findByClassAndUser(classid,Userid).isEmpty())
            {userClassRole.setUser(userRepository.findById(Userid).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED)));
            userClassRole.setClasz(claszRepository.findById(classid).orElseThrow(()-> new AppException(ErrorCode.CLASS_NOT_EXISTED)));}
        else{
            return "thanh vien nay da ton tai";
        }
        userClassRole.setRole(UserClassRole.Role.STUDENT);
        userClassRolerRepostiory.save(userClassRole);
        return "Thêm thành viên thành công";
    }
    public String deleteclass(String id){
        Clasz clas= claszRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.CLASS_NOT_EXISTED));
        clas.setIsdelete(true);
        clas=claszRepository.save(clas);
        List<UserClassRole> userClassRoles=userClassRolerRepostiory.findByClasz_Classid(clas.getClassid());

        for (UserClassRole userClassRole : userClassRoles) {
            userClassRole.setIsdelete(true);  // Cập nhật cột delete
        }
        userClassRolerRepostiory.saveAll(userClassRoles);

        return "xóa lớp thanh công";
    }
    public String deletethanhvien(String classid, String userid){
        
        UserClassRole userClassRole = userClassRolerRepostiory.findByClassAndUser(classid,userid).orElseThrow(() -> new AppException(ErrorCode.CLASS_USER_NOT_EXISTED));
        userClassRolerRepostiory.delete(userClassRole);

        return "xóa thành công";
    }
    public List<UserofClassResponse> getclassbyuser(String Classid){
        List<String> userid = userClassRolerRepostiory.findByUserStudentandnotdelete(Classid);
        List<User> users = userRepository.findAllById(userid);
        
        List<UserofClassResponse> userofClassResponses = new ArrayList<>();
        for (User user : users){
            UserofClassResponse response = userMapper.toUserofClassResponse(user);
            userofClassResponses.add(response);
        }
        return userofClassResponses;

    }

}
