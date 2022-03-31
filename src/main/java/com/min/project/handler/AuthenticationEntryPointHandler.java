//package com.min.project.handler;
//
//import com.min.project.ErrorCode;
//import lombok.RequiredArgsConstructor;
//import org.json.JSONObject;
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//@Component
//@RequiredArgsConstructor
////스프링 시큐리티 컨택스트 내에 존재하는 인증 절차 중 인증과정이 실패하거나 인증헤더(Authorization)를 보내지 않게 되는 경우 401이라는 응답값을
////던지는데 이를 처리해주는 인터페이스이다. 401이 떨어질만한 에라가 발생한 경우 commerce() 메서드가 실행된다
//public class AuthenticationEntryPointHandler  implements AuthenticationEntryPoint {
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authExecption) throws IOException, ServletException {
//        String exeception = (String)request.getAttribute("exception");
//        ErrorCode errorCode;
//
////        토큰이 없는 경우 예외처리
//        if(exeception==null){
//            errorCode=ErrorCode.UNAUTHORIZEDEException;
//            setResponse(response,errorCode);
//
//        }
//
////        토큰이 만료된 경우
//        if(exeception.equals("ExpiredJwtException")){
//            errorCode=ErrorCode.ExpiredJwtException;
//            setResponse(response,errorCode);
//            return;
//        }
//    }
//    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
//        response.setContentType("application/json;charset=UTF-8");
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//
//        JSONObject responseJson = new JSONObject();
//        responseJson.put("message", errorCode.getMessage());
//        responseJson.put("code", errorCode.getCode());
//
//        response.getWriter().print(responseJson);
//    }
//}
