//package com.min.project.provider;
//
//import com.min.project.config.auth.MyUserDetails;
//import com.min.project.config.auth.MyUserDetailsService;
//import com.min.project.dto.UserDto;
//import io.jsonwebtoken.*;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.servlet.ServletRequest;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Locale;
//import java.util.Map;
//
//@Slf4j
//@RequiredArgsConstructor
//@Component
//public class JwtProvider {
////    시크릿키 여기다가 써도 되나 ?
//    private final String secretKey = "asdasd";
//    private  final  long accessExpireTime=1*60*1000L; //1분
//    private final long refreshExpireTime = 2* 60*2000L;//2분
//    private final MyUserDetailsService myUserDetailsService;
//
//    public String createAccessToken(UserDto userDto){
//        Map<String, Object> headers =new HashMap<>();
//        headers.put("type","token");
//
//        Map<String, Object> payloads = new HashMap<>();
//        payloads.put("email", userDto.getEmail());
//
//        Date expiration = new Date();
//        expiration.setTime(expiration.getTime()+accessExpireTime);
//
//        String jwt = Jwts
//                .builder()
//                .setHeader(headers)
//                .setClaims(payloads)
//                .setSubject("user")
//                .setExpiration(expiration)
//                .signWith(SignatureAlgorithm.HS256, secretKey)
//                .compact();
//        return jwt;
//    }
//
//    public Map<String, String> createRefreshToken(UserDto userDto){
//        Map<String, Object> headers =new HashMap<>();
//        headers.put("type","token");
//
//        Map<String, Object> payloads = new HashMap<>();
//        payloads.put("email", userDto.getEmail());
//        Date expiration = new Date();
//        expiration.setTime(expiration.getTime()+refreshExpireTime);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
//        String refreshTokenExpireation =simpleDateFormat.format(expiration);
//
//        String jwt = Jwts
//                .builder()
//                .setHeader(headers)
//                .setClaims(payloads)
//                .setSubject("user")
//                .setExpiration(expiration)
//                .signWith(SignatureAlgorithm.HS256, secretKey)
//                .compact();
//        Map<String,String> result = new HashMap<>();
//        result.put("refrshToken",jwt);
//        result.put("refreshTokenExpireation",refreshTokenExpireation);
//        return result;
//    }
//
//    public Authentication getAuthentication(String token){
//        UserDetails userDetails = myUserDetailsService.loadUserByUsername(this.getUserInfo(token));
//        return new UsernamePasswordAuthenticationToken(userDetails,"", userDetails.getAuthorities());
//    }
//
//    public String getUserInfo(String token){
//        return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("email");
//    }
//
//    public String resolveToken(HttpServletRequest request){
//        return request.getHeader("token");
//    }
//
//    public boolean validateJwtToken(ServletRequest request, String authToken){
//        try{
//            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
//            return true;
//        } catch (MalformedJwtException e){
//            request.setAttribute("exception","MalformedJwtException");
//        }catch (ExpiredJwtException e){
//            request.setAttribute("exception","ExpiredJwtException");
//        }catch (UnsupportedJwtException e){
//            request.setAttribute("exception","UnsupportedJwtException");
//        }catch (IllegalArgumentException e){
//            request.setAttribute("exception","IllegalArgumentException");
//        }
//        return false;
//    }
//}
//
