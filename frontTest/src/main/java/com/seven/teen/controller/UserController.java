package com.seven.teen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seven.teen.entity.User;
import com.seven.teen.service.UserService;


@Controller
public class UserController 
{
    @Autowired
    private UserService userService;


    @ResponseBody
    @GetMapping("/test")   
    public List<User> testResult()
    {
        User user = new User();
        
        user.setId(1L);
        user.setUsername("TEST");
        user.setPassword("Password");

        userService.saveUser(user);


        return userService.getAllUsers();
    }     

    @GetMapping("/stop")
    public String stopWatch()
    {
        return "stopWatch.html";
    }

    @GetMapping("/enter")
    public String enterCnt()
    {
        return "enterCnt.html";
    }
}
