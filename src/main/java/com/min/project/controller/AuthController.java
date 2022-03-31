//package com.min.project.controller;
//
//import com.min.project.Response.ApiResponse;
//import com.min.project.dto.AuthDTO;
//import com.min.project.dto.UserDto;
//import com.min.project.service.AuthService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.naming.AuthenticationException;
//import javax.servlet.http.HttpServletRequest;
//
//@CrossOrigin(origins = "http://http://localhost:8080/")
//@RestController
//@RequiredArgsConstructor
//public class AuthController {
//    @Autowired
//    private AuthService authService;
//
//    @PostMapping("/auth/login")
//    public ApiResponse login(UserDto userDto) throws AuthenticationException {
//        System.out.println("/auth/login 도착 "+userDto.toString());
//        return authService.login(userDto);
//    }
//
//    @PostMapping("/auth/get-newToken")
//    public ApiResponse newAccessToken(AuthDTO.GetNewAccessTokenDto getNewAccessTokenDto,
//                                      HttpServletRequest request){
//        return authService.newAccessToken(getNewAccessTokenDto, request);
//    }
//}
