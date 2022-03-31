package com.min.project.config;

import com.min.project.config.auth.MyUserDetailsService;
//import com.min.project.filter.JwtAuthenticationFilter;
//import com.min.project.handler.AuthenticationEntryPointHandler;
//import com.min.project.handler.WebAccessDeniedHandler;
//import com.min.project.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
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

    @Bean
    public BCryptPasswordEncoder Encoder() {
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
        web
                .ignoring().antMatchers();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
                    .authorizeRequests()
                    .antMatchers("/admin").access("hasRole('ADMIN')")
                    .antMatchers("/", "/register", "/auth/register1","/board/","/vue/board","/auth/login","/auth/get-newToken","/loginview").permitAll()
                    .anyRequest().access("hasRole('ADMIN') or hasRole('USER')")
                .and()
                    .formLogin()
                    .loginPage("/loginview")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .loginProcessingUrl("/authenticate")
                    .failureForwardUrl("/members/loginerror?login_error=1")
                    .defaultSuccessUrl("/", true)
                    .permitAll()
                .and()
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
    @Override
    protected void configure(AuthenticationManagerBuilder
                                     authManagerBuilder) throws Exception {
        authManagerBuilder.userDetailsService(myUserDetailsService);
    }
}
