package com.team.team_project.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;



@Configuration
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // 비밀번호 암호화
    // 비밀번호이기 때문에 복호화를 불가능하게 만듬



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()
                .withUser("singsiuk")
                .password("$2a$10$4Bt6CjgPfdLNp9I17NO/5OIPkBg1Fu..s57CfTa/wiyBLzEO00rkW")
                .roles("ADMIN");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/checkplan/**","/find/**","forUser") //해당 경로들은
                .permitAll() //접근을 허용한다.
                .anyRequest() //다른 모든 요청은
                .authenticated() //인증이 되야 들어갈 수 있다.
                .and() // 그리고
                .formLogin() //로그인 폼은
                .loginPage("/checkplan/mainpage") //로그인 페이지를 우리가 만든 페이지로 등록한다.
                .loginProcessingUrl("/login")//스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인해줌(서비스의 loadUserByName로 알아서)
                .defaultSuccessUrl("/checkplan/mainpage"); //정상일떄
        //중복 로그인
        http.sessionManagement()
                .maximumSessions(1) //세션 최대 허용 수
                .maxSessionsPreventsLogin(false); // false이면 중복 로그인하면 이전 로그인이 풀린다.
    }
}
