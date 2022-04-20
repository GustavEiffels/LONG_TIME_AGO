package com.example.memoserver.controller;

import com.example.memoserver.service.Email.ForJoin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email/")
@Slf4j
public class EmailController
{
    @Autowired
    private ForJoin emailSenderService;

    @PostMapping("auth")
    public String emailAuth(String email)
    {
        String result =emailSenderService.excuteGenerate();
        log.info("email={}", result);

        return result;
    }
    /**
     * test
     */
}
