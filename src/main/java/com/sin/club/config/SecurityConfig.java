package com.sin.club.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        // 암호화해서 평문과 비교는 할 수 있다.
        // 그러나 복호화를 할 수 없는 클래스의
        // 인스턴스를 리턴해서 생성
        return new BCryptPasswordEncoder();
    }

    // 설정하는 method
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        // in memory 에 유저 생성
        auth.inMemoryAuthentication()
                .withUser("user1")
                .password("$2a$10$EDfYFSUlswKwFiQF/0KXk.pm3TkiurL8kc148FLGgJ0TeCe6op6EK")
                .roles("USER");
    }

    // 인가 설정 Override
    @Override
    protected void configure(HttpSecurity http)throws Exception{
        //sample / all 은 로그인 여부와 상관없이 접근 가능
        // sample/member 는 USER 권한이 있어야만 접근 가능
        http.authorizeRequests()
                .antMatchers("/sample/all").permitAll()
                .antMatchers("/sample/member").hasRole("USER");

        // 권한이 없는 경우 로그인 page 로 이동
        // 로그인 요청 URL 은 Custmlogin 이고 URL 처리는 login
        http.formLogin()
                .loginPage("/customlogin")
                .loginProcessingUrl("/login");


        // csrf 토큰을 비교하는 작업을 수행하지 않음
        http.csrf().disable();
    }
}
