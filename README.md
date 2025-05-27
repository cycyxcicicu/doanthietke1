DỰ ÁN LÀ SẢN PHẨM ĐỒ ÁN MÔN HỌC THIẾT KẾ KIẾN TRÚC PHẦN MỀM  
ĐỀ TÀI: XÂY DỰNG HỆ THỐNG TRỘN ĐỀ TRẮC NGHIỆM ONLINE

FRONTEND: ReactJS (folder cauhoi)
BACKEND: Spring Boot (folder cauhoi2)

-------------------------------------
Cấu hình file application.yaml
-------------------------------------
server:
  port: 8080            # Cổng chạy ứng dụng
  servlet:
    context-path: /cauhoi  # Đường dẫn gốc cho REST API

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/
    username: 
    password: 
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    hibernate:
      ddl-auto: update  
    show-sql: true
