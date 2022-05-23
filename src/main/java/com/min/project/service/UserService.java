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

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    @Autowired
    JoinDao joindao;
    @Autowired
    LoginDao loginDao;


    private final LoginDao logindao;

    private final BCryptPasswordEncoder encoder;
//    @Autowired
//    BCryptPasswordEncoder encoder;

    //회원가입
    @Transactional
    public boolean register(UserForm form) {
        UserDto userDto = new UserDto();
        System.out.println("-----------서비스--------");
        if (joindao.findByEmail(form.getEmail()) == null) {
            System.out.println("데이터 확인:" + userDto.toString());
            userDto.setEmail(form.getEmail());
            userDto.setPassword(encoder.encode(form.getPassword()));
            userDto.setUsername(form.getUsername());
            userDto.setRole("ROLE_ADMIN");
            userDto.setCheckEmail(form.getCheckEmail());
            joindao.save(userDto);
//            return logindao.save(form.toEntity()).getId();
            return true;
        } else {
            System.out.println("-----------여기?--------");
            return false;
        }
    }

    public UserForm authenticate(String email, String password) {
        UserForm userForm =new UserForm();
        Optional<UserDto> userFormOP = loginDao.findByEmail(email);
        if (userFormOP.isPresent()) {
            System.out.println("null 입니다1");
            UserDto userDto = userFormOP.get();
            System.out.println("비번 체크:"+encoder.matches(password, userDto.getPassword()));
            if (encoder.matches(password, userDto.getPassword())) {
                userForm.setRole(userDto.getRole());
                userForm.setEmail(userDto.getEmail());
                userForm.setUsername(userDto.getUsername());
                userForm.setPassword(userDto.getPassword());
                userForm.setCheckEmail(userDto.getCheckEmail());
                return userForm;
            }
        }
        System.out.println(password);
        System.out.println("null 입니다2");
        return null;
    }
}