package com.example.cauhoi2.entity;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity(name = "class")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Clasz {
    @Id
    String classId;

    @OneToMany(mappedBy = "testId")
    List<Test> testId;

    String name;
    String imageClass;
    Boolean isDelete;
    @OneToMany(mappedBy = "clasz")
    private Set<UserClassRole> userClassRoles;

    @PrePersist
    public void generateClassId() {
        if (this.classId == null || this.classId.isEmpty()) {
            this.classId = generateShortClassId();  // Tạo ID ngẫu nhiên nếu chưa có
        }
    }

    // Phương thức tạo classId ngắn từ UUID
    private String generateShortClassId() {
        String uuid = UUID.randomUUID().toString().replace("-", "");  // Tạo UUID và loại bỏ dấu gạch ngang
        return uuid.substring(0, 5);  // Lấy 5 ký tự đầu tiên của UUID
    }
}
