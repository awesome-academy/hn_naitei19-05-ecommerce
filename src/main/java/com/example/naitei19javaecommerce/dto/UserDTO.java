package com.example.naitei19javaecommerce.dto;

import com.example.naitei19javaecommerce.model.UserDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private Integer role;
    private UserDetail userDetail;
    private Byte isActive;
    private LocalDateTime createdAt;

    //user detail
    private String phone;
    private String address;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime modifiedAt;
}
