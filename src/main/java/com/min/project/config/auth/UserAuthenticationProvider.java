package com.min.project.config.auth;

import com.min.project.form.UserForm;
import com.min.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("UserAuthenticationProvider에 도착");
        String email = authentication.getName();
        String password = (String) authentication.getCredentials();
        System.out.println("password:"+password);
        UserForm userForm = userService.authenticate(email, password);
        if (userForm == null) {
//            return new UsernamePasswordAuthenticationToken(null,null,null);
            System.out.println("에러 ?");
//            return null;
            throw new BadCredentialsException(email);
        }
        userForm.setPassword(null);

        ArrayList<SimpleGrantedAuthority> authorities =new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userForm.getRole()));
        System.out.println("authorities: "+ authorities);
        return new UsernamePasswordAuthenticationToken(userForm,null,authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
