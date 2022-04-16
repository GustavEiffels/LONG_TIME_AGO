package com.example.memoserver.controller;

import com.example.memoserver.service.DupCheck.DupCheckService;
import com.example.memoserver.service.User.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/")
public class TestController
{
    @Autowired
    private UserService userService;

    @Autowired
    private DupCheckService dupCheckService;

    @GetMapping("test")
    public String test1()
    {
        return "This is test data";
    }

    @PostMapping("save")
    public String saveTest(HttpServletRequest request, HttpServletResponse response)
    {

        String userId = request.getParameter("userId");
        String userPw = request.getParameter("userPw");
        String userNick = request.getParameter("userNick");
        String userEmail = request.getParameter("userEmail");

        log.info("id",userId);
        log.info("Pw",userPw);
        log.info("nick",userNick);

        userService.saveUser(userId,userPw,userNick,userEmail);

        return "success";
    }

    @PostMapping("/idDuplicate")
    private void idDuplicateCheck(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        /**
         *  result --> true  : 중복되는 id 가 존재하지 않는다.
         *  result --> fasle : 중복되는 nick 이 존재한다 .
         */
        boolean result = dupCheckService.idDuplicateCheck(request.getParameter("userId"));
        log.info("result", result);
        log.info("userId", request.getParameter("userId") );

        System.out.println(request.getParameter("userId"));

        if(!result)
        {

            response.getWriter().write(String.valueOf("This ID is being used by someone"));

        }

    }

    @PostMapping("/nickDuplicate")
    private void nickDuplicateCheck(HttpServletRequest request, HttpServletResponse response) throws IOException
    {

        /**  false --> 중복
         *   true ---> 중복아님
         */
        if(!dupCheckService.nickDuplicateCheck(request.getParameter("userNick")))
        {
            log.debug("userNick", dupCheckService.nickDuplicateCheck(request.getParameter("userNick")));
            response.getWriter().write("This nick is being used by someone");
        }
    }





}
