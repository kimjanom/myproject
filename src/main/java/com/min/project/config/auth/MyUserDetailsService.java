package com.min.project.config.auth;


//import com.min.project.ErrorCode;
import com.min.project.dao.LoginDao;
import com.min.project.dto.UserDto;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



@Service
public class MyUserDetailsService implements UserDetailsService {

//    UserDbService userdbService;

    @Autowired
    private LoginDao loginDao;




    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

//            UserDto userDto = loginDao.findByEmail(loginId).get();
//            if (userDto == null) {
//                throw new AuthenticationException(ErrorCode.UsernameorPasswordNotFoundException.getMessage());
//            }
//            return (UserDetails) userDto;



        System.out.println("----------loadUserByUsername----------------");
        System.out.println("----------email:"+loginId);
        UserDto user = loginDao.findByEmail(loginId).orElseThrow(()->
                new UsernameNotFoundException("해당 사용자가 존재하지 않습니다.:"+loginId));
        MyUserDetails myUserDetails=new MyUserDetails();
        System.out.println("-----------------------myUserDetails.getPassword:"+myUserDetails.getPassword());
        myUserDetails.setUsername(user.getEmail());

        myUserDetails.setPassword(user.getPassword());
        System.out.println("-----------------------myUserDetails.getPassword2:"+myUserDetails.getPassword());

        System.out.println("----------userget email:"+loginId);
        System.out.println("----------user.getPassword():"+user.getPassword());
//        List<UserRoleEntity> customRoles = userdbService.getUserRoles(user.getEmail());
//        // 로그인 한 사용자의 권한 정보를 GrantedAuthority를 구현하고 있는 SimpleGrantedAuthority객체에 담아
//        // 리스트에 추가한다. MemberRole 이름은 "ROLE_"로 시작되야 한다.
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        ArrayList<UserDto> authorities = (ArrayList<UserDto>)loginDao.findByEmail(loginId).get();
//        if(customRoles != null) {
//            for (UserRoleEntity customRole : customRoles) {
//                authorities.add(new SimpleGrantedAuthority(customRole.getRoleName()));
//            }
//        }
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        System.out.println("-----------------------1:");
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        System.out.println("-----------------------2:");
        System.out.println("-----------------------authorities:"+authorities);
        // CustomUserDetails객체에 권한 목록 (authorities)를 설정한다.
        myUserDetails.setAuthorities(authorities);
        System.out.println("-----------------------myUserDetails.getAuthorities():"+myUserDetails.getAuthorities());
        myUserDetails.setEnabled(true);
        System.out.println("-----------------------3:");
        myUserDetails.setAccountNonExpired(true);
        myUserDetails.setAccountNonLocked(true);

        myUserDetails.setCredentialsNonExpired(true);
        System.out.println("-----------------------3:");

//        session.setAttribute("user", new UserSessionDto(user));
        return myUserDetails ;

//        Optional<UserDto> userEntityWrapper =loginDao.findByEmail(email);
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        UserDto memberEntity = userEntityWrapper.orElse(null);
//
//        authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
//
//        return new User(memberEntity.getEmail(),memberEntity.getPassword(),authorities);
    }

//    @Transactional
//
//    public Integer save(UserForm userForm){
//
//    }
}
