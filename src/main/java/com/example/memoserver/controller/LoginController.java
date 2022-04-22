package com.example.memoserver.controller;


import com.example.memoserver.service.Login.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController
{
    @Autowired
    private LoginService loginService;


    /** Login 을 위한 method  ----------------------------------------------------------------*/
    /** LoginFragment */
    @PostMapping("")
    public String login(String userId, String userPw, int user_autologin) throws IOException
    {
        // 입력한 정보가 알맞을 경우, userIdx 를 반환
        // 입력한 정보가 알맞지 않을 경우 , ErrorMessage 를 반환
        Map<String,Object> result = loginService.login(userId,userPw);

        // auto Login 값을 변경
        loginService.autoUpdate(user_autologin, userId);

        String answer = "";


        // 에러 message 가 없는 경우 ---> 정상적으로 로그인
        if( result.get("errorMessage")==null )
        {
            answer = String.valueOf(result.get("userIdx"));
        }
        // 에러 message 가 존재하는 경우 ----> 없는 아이디 이거나 비밀번호 틀렸을 경우
        else
        {
            answer = String.valueOf(result.get("errorMessage"));
        }
        return  answer;
    }


    /** 해당 user 의 자동로그인 여부를 return 받음 ----------------------------------------------------------------------------*/
    /** LoginFragment */
    @PostMapping("/getAutoLoginInfo")
    public String getAutoLoginInfo(String userIdx) throws IOException
    {
        return loginService.getUserAutoInfo( Long.valueOf(userIdx) );
    }


    /** 로그인 성공 시 해당 로그인한 user 의 nickname 을 받아오는 method ---------------------------------------------------------- */
    /** LoginFragment */
    @PostMapping("getUserNick")
    public String getUserNick(String user_idx)
    {
        return loginService.getUserNick(Long.valueOf(user_idx));
    }



    /** Logout method -------------------------------------------------------------------------------------------------- */
    /** settingFragment */
    @PostMapping("logout")
    public void logout(String user_idx, int user_auto_login) { loginService.logout(Long.valueOf(user_idx), user_auto_login); }






    /** Google 계정이 존재하는지 확인하는 Method ---------------------------------------------------------------------------- */
    /** LoginFragment */
    @PostMapping("googleCheck")
    public String googleCheck(String email)
    {
        return loginService.googleCheck(email);
    }



    /** Google 계정이 존재할 때, 계정 정보를 반환하는 method */
    /** LoginFragment */
    @PostMapping("googleAccount")
    public String googleAccount(String email)
    {
        return loginService.googleAccount(email).toString();
    }





}
