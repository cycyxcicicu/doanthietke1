package com.example.cauhoi2.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequest {
     @NotBlank(message = "USERNAME_IS_REQUIRED")
     String username;
     @Size(min=8, message ="PASSWORD_INVALID")
     String password;
     @NotBlank(message = "NICKNAME_IS_REQUIRED")
     String nickname;
}
