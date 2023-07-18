package com.example.memoserver.controller;

import com.example.memoserver.service.Find.FindService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/find/")
public class FindController
{

    @Autowired
    private FindService findService;

    @PostMapping("account")
    public String findAccount(String email)
    {
        return findService.findAccount(email);
    }

    @PostMapping("password")
    public String findPassword(String email , String id)
    {
        return findService.findPassword(email, id);
    }
}
