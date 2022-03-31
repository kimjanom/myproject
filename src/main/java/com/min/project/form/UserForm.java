package com.min.project.form;

import com.min.project.dto.UserDto;
import com.min.project.Role.Role;
import lombok.Data;


@Data
public class UserForm {
    private String username;
    private String password;
    private String email;
    private String role;

//    public UserDto toEntity(){
//        UserDto user =UserDto.builder()
//                .username(username)
//                .password(password)
//                .email(email)
//                .role(role.USER)
//                .build();
//        return user;
//
//
//    }


}
