package com.min.project.dto;

import com.min.project.Role.Role;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


import javax.persistence.*;



//@Builder
@Data
@Entity
@RequiredArgsConstructor
public class UserDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = true, length= 60)
    private String password;
    @Column(nullable = false, length = 60)
    private String username;
    @Column(nullable = false, length = 60)
    private String email;

    @Column(nullable = false)
    private String role;


}
