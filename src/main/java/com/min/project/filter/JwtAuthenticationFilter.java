//package com.min.project.filter;
//
//import com.min.project.provider.JwtProvider;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.GenericFilterBean;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends GenericFilterBean {
//    private  final JwtProvider jwtProvider;
//
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
//        //헤더에서 jwt를 받아온다
//        String token =jwtProvider.resolveToken((HttpServletRequest) request);
//
//        //유요한 토큰인지 확인
//        if(token != null &&jwtProvider.validateJwtToken(request, token)){
//
//            //토큰이 유요하면 토큰으로부터 유저 정보를 받아온다
//            Authentication authentication =jwtProvider.getAuthentication(token);
//
//            //SecurityContext 에 AUthentication 객체를 저장합니다
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//        filterChain.doFilter(request,response);
//    }
//}
