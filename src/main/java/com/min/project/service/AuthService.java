//package com.min.project.service;
//
//import com.min.project.ErrorCode;
//import com.min.project.Response.ApiResponse;
//import com.min.project.Response.ResponseMap;
//import com.min.project.dao.AuthDao;
//import com.min.project.dao.LoginDao;
//import com.min.project.dto.AuthDTO;
//import com.min.project.dto.UserDto;
//import com.min.project.dto.RefreshToken;
//import com.min.project.provider.JwtProvider;
//import lombok.RequiredArgsConstructor;
//import org.postgresql.jdbc.PreferQueryMode;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.stereotype.Service;
//
//import javax.naming.AuthenticationException;
//import javax.servlet.http.HttpServletRequest;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class AuthService {
//    private final JwtProvider jwtProvider;
//    private final AuthenticationManager authenticationManager;
//    private final LoginDao loginDao;
//    private final AuthDao authDao;
//
//    public ApiResponse login(UserDto userDto) throws AuthenticationException {
//        System.out.println("");
//        ResponseMap result = new ResponseMap();
//
//        try{
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(userDto.getEmail(),userDto.getPassword())
//            );
//
//            Map createToken = createTokenReturn(userDto);
//            result.setResponseData("accessToken",createToken.get("accessToken"));
//            result.setResponseData("refreshIdx",createToken.get("refreshIdx"));
//        } catch (Exception e){
//            e.printStackTrace();
//            throw new AuthenticationException(ErrorCode.UsernameorPasswordNotFoundException.getMessage());
//        }
//        return result;
//    }
//
//    public ApiResponse newAccessToken(AuthDTO.GetNewAccessTokenDto getNewAccessTokenDto, HttpServletRequest request){
//        ResponseMap result = new ResponseMap();
//        String refreshToken = authDao.findByRefreshTokenOrderById(getNewAccessTokenDto.getRefreshId());
//        //AccessToken은 만료되었지만 RefreshToken은 만료되지 않은 경우
//        if(jwtProvider.validateJwtToken(request, refreshToken)){
//            String email = jwtProvider.getUserInfo(refreshToken);
//            UserDto userDto=new UserDto();
//            userDto.setEmail(email);
//
//            Map createToken = createTokenReturn(userDto);
//            result.setResponseData("accsessToken", createToken.get("accessToken"));
//            result.setResponseData("refreshId", createToken.get("refreshId"));
//        }else{
//            // RefreshToken 또한 만료된 경우는 로그인을 다시 진행해야 한다
//            result.setResponseData("code", ErrorCode.Relogin.getCode());
//            result.setResponseData("message", ErrorCode.Relogin.getMessage());
//            result.setResponseData("status", ErrorCode.Relogin.getStatus());
//        }
//        return result;
//    }
//
//    private Map<String, String> createTokenReturn(UserDto userDto){
//        Map result = new HashMap();
//
//        String accessToekn =jwtProvider.createAccessToken(userDto);
//        String refreshToken =jwtProvider.createRefreshToken(userDto).get("refreshToken");
//        String refreshTokenExpirationAt=jwtProvider.createRefreshToken(userDto).get("refreshTokenExpirationAt");
//
//        RefreshToken insertRefreshToken = RefreshToken.builder()
//                .email(userDto.getEmail())
//                .accessToken(accessToekn)
//                .refreshToken(refreshToken)
//                .refreshTokenExpirationAt(refreshTokenExpirationAt)
//                .build();
//        //https://kdg-is.tistory.com/228 에서는 inserorupdate를 사용했지만 jpa는 save가 insert와 update를 알아서 하는걸로 알고있다
//        try{
//            authDao.save(insertRefreshToken);
//        }catch (Exception e){
//            System.out.println("authService에서 예외 발생:"+e);
//        }
//
//        result.put("accessToken",accessToekn);
//        result.put("refreshIdx",insertRefreshToken.getId());
//        return result;
//    }
//}
