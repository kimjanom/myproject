package com.min.project.config.auth;

import com.min.project.dao.LoginDao;
import com.min.project.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private String secretKey = "webfirewood";

    //토큰유효시간 30분
    private long tokenValidTime = 30 * 60 * 1000L;


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    LoginDao loginDao;

    //객체 초기화, secretKey를 base64로 인코딩
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());

    }

    //JWT 토큰 생성
    public String createToken(String userEmail, String userRole) {
        Claims claims = Jwts.claims().setSubject(userEmail); // JWT payload에 저장되는 정보단위
        claims.put("roles", userRole);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) //set Expire time
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    //JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        Optional<UserDto> userDto = loginDao.findByEmail(getUserPk(token));
        if (userDto.isPresent()) {
            UserDto userDetail = userDto.get();
            ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(userDetail.getRole()));
//            - principal : 아이디 - credential : 비밀번호
//            출처: https://flyburi.com/584 [버리야 날자]
            return new UsernamePasswordAuthenticationToken(userDto, null, authorities);
        } else {
            return null;
        }
    }


    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            return null;
        }
        for(Cookie cookie : cookies){
            cookie.getName();
            cookie.getValue();
            if(cookie.getName() == "X-AUTH-TOKEN"){
                return cookie.getValue();
            }
        }
        return null;
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}