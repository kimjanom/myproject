package com.min.project.config;

import com.min.project.config.auth.JwtTokenProvider;
import com.min.project.config.auth.MyUserDetailsService;
//import com.min.project.filter.JwtAuthenticationFilter;
//import com.min.project.handler.AuthenticationEntryPointHandler;
//import com.min.project.handler.WebAccessDeniedHandler;
//import com.min.project.provider.JwtProvider;
import com.min.project.config.auth.UserAuthenticationProvider;
import com.min.project.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AnonymousAuthenticationProvider;
//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    private final JwtProvider jwtProvider;
    private final MyUserDetailsService myUserDetailsService;
//    private final AuthenticationEntryPointHandler authenticationEntryPointHandler;
//    private final WebAccessDeniedHandler webAccessDeniedHandler;

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean()throws Exception{
        return super.authenticationManagerBean();
    }
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(myUserDetailsService);
//    }

//    @Bean(BeanIds.AUTHENTICATION_MANAGER)
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    @Override
//    인증을 무실할 경로를 설정해준다
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors()
                .and()
                .csrf().disable()
                .sessionManagement()
//                .sessionCreationPolicy( SessionCreationPolicy.정책상수)
//        SessionCreationPolicy.ALWAYS        - 스프링시큐리티가 항상 세션을 생성
//
//        SessionCreationPolicy.IF_REQUIRED - 스프링시큐리티가 필요시 생성(기본)
//
//                SessionCreationPolicy.NEVER           - 스프링시큐리티가 생성하지않지만, 기존에 존재하면 사용
//
//        SessionCreationPolicy.STATELESS     - 스프링시큐리티가 생성하지도않고 기존것을 사용하지도 않음
//                                                                  ->JWT 같은토큰방식을 쓸때 사용하는 설정
//                .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()

                .and()
                    .authorizeRequests()
                    .antMatchers("/admin").access("hasRole('ADMIN')")
                    .antMatchers("/", "/register", "/auth/register1","/board/","/vue/board","/auth/login","/auth/get-newToken","/loginview","/getsession","/vue/loginerror", "/error","/vue/login","/vue/album/detail","/audioLink/*","/imageLink/*","/vue/*","/upload/media").permitAll()
                    .anyRequest().access("hasRole('ADMIN') or hasRole('USER')")
                .and()
                    .formLogin()
                    .loginPage("/loginview")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .loginProcessingUrl("/authenticate")
                    .successForwardUrl("/")
                    .failureUrl("/loginview")
                //.failureForwardUrl 이거 오류 나는중 어케 사용하는건지 찾아보자
//                    .failureForwardUrl("/loginview")
                    .defaultSuccessUrl("/")
                    .permitAll()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
//                    .exceptionHandling()
//                    .authenticationEntryPoint(authenticationEntryPointHandler)
//                    .accessDeniedHandler(webAccessDeniedHandler)
//                .and()
//                    .addFilterBefore(new JwtAuthenticationFilter(jwtProvider),
//                        UsernamePasswordAuthenticationFilter.class);
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");

    }
    @Bean
    //cors 관련 설정
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("http://localhost:8080/");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Autowired
    private UserAuthenticationProvider userAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder
                                     authManagerBuilder) throws Exception {

        authManagerBuilder.authenticationProvider(userAuthenticationProvider);
        authManagerBuilder.userDetailsService(myUserDetailsService);
    }
}
