package com.min.project.service;

import com.min.project.dao.JoinDao;
import com.min.project.dao.LoginDao;
import com.min.project.dto.UserDto;
import com.min.project.form.UserForm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    @Autowired
    JoinDao joindao;



    private final LoginDao logindao;

    private final BCryptPasswordEncoder encoder;
//회원가입
    @Transactional
    public boolean register(UserForm form) {
        UserDto userDto=new UserDto();
        System.out.println("-----------서비스--------");
        if(joindao.findByEmail(form.getEmail())== null) {
            System.out.println("데이터 확인:"+userDto.toString());
            userDto.setEmail(form.getEmail());
            userDto.setPassword(encoder.encode(form.getPassword()));
            userDto.setUsername(form.getUsername());
            userDto.setRole("ROLE_ADMIN");
            joindao.save(userDto);
//            return logindao.save(form.toEntity()).getId();
            return true;
        }else{
            System.out.println("-----------여기?--------");
            return false;
        }
    }

}