package com.example.memoserver.controller;

import com.example.memoserver.service.DupCheck.DupCheckService;
import com.example.memoserver.service.Login.LoginService;
import com.example.memoserver.service.User.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("join")
public class JoinController
{

    @Autowired
    private UserService userService;

    @Autowired
    private DupCheckService dupCheckService;

    @Autowired
    private LoginService loginService;

    // -----------------------------------------------------------------------------------------------------------------


    /** 이메일 중복 확인 ---------------------------------------------------------------------------------------------------
     */
    @PostMapping("emailCheck")
    public String emailCheck(String email)
    {
        return loginService.emailCheck(email);
    }




    /** Id 중복확인 method -----------------------------------------------------------------------------------------------
     *  IdAndPwFragment
     */
    @PostMapping("/idDuplicate")
    private String idDuplicateCheck(String userId)
    {
        /**
         *  result --> true  : 중복되는 id 가 존재하지 않는다.
         *  result --> fasle : 중복되는 nick 이 존재한다 .
         */
        boolean result = dupCheckService.idDuplicateCheck(userId);

        String answer = "";

        if(!result)
        {
            answer = "N";
        }
        return  answer;

    }

    /** Nick 중복확인 method -----------------------------------------------------------------------------------------------
     *  JoinFragment
     */
    @PostMapping("/nickDuplicate")
    private String nickDuplicateCheck(String userNick)
    {
        // true ---> 중복 아님
        // false ---> 중복
        String result ="";
        if( !dupCheckService.nickDuplicateCheck(userNick) )
        {
            result = "This nick is being used by someone";
        }
        return  result;
    }


    /** 회원가입 완료 method ------------------------------------------------------------------------
     * JoinFragment
     */
    @PostMapping("")
    public String saveTest(String userId, String userPw, String userNick, String userEmail)
    {
        userService.saveUser(userId,userPw,userNick,userEmail);
        return "success";
    }
}
