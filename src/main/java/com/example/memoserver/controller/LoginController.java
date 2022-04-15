package com.example.memoserver.controller;


import com.example.memoserver.service.Login.LoginService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController
{
    @Autowired
    private LoginService loginService;


    @PostMapping("")
    public void loginController(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        /** return Map<String, Object></>
         */
        Map<String,Object> result =
                loginService.login(request.getParameter("userId"),request.getParameter("userPw"));

        loginService.autoUpdate(Integer.parseInt(request.getParameter("user_autologin")), request.getParameter("userId"));


        /** 로그인이 정상적으로 되었을 때,
         */
        if(result.get("errorMessage")==null)
        {
            response.getWriter().write(String.valueOf(result.get("userIdx")));
        }
        else
        {
            response.getWriter().write(String.valueOf(result.get("errorMessage")));
        }
    }

    @PostMapping("/getAutoLoginInfo")
    public void getAutoLoginInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
    {

        Long result = Long.valueOf(request.getParameter("userIdx"));

        response.getWriter().write(loginService.getUserAutoInfo(result));
    }
    @PostMapping("getUserNick")
    public String getUserNick(String user_idx)
    {

        return loginService.getUserNick(Long.valueOf(user_idx));
    }


    @PostMapping("logout")
    public void logout(String user_idx, int user_auto_login)
    {   log.info("user_idx = {}" ,user_idx);
        log.info("user_auto_login = {}", user_auto_login);
        loginService.logout(Long.valueOf(user_idx), user_auto_login);
    }

    @PostMapping("getPassword")
    public String getUserPassword(String user_idx, String get_password)
    {
        if( !get_password.equals( loginService.getUserPassword( Long.valueOf(user_idx) ) ) )
        {
            return "Wrong Password";
        }
        return "collect";
    }

    @PatchMapping("changePw")
    public void updatePw(String newPw, Long user_idx)
    {
        loginService.changePw(0, newPw, user_idx);
    }

}
