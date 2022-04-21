package com.example.memoserver.controller;

import com.example.memoserver.service.Email.EmailSenderService;
import com.example.memoserver.service.Email.ForJoin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/email/")
@Slf4j
public class EmailController
{
    @Autowired
    private ForJoin forJoin;

    @Autowired
    private EmailSenderService emailSenderService;



    @PostMapping("auth")
    public String emailAuth(String email) throws MessagingException {
        String result =forJoin.excuteGenerate();

        emailSenderService.sendMail("Hello this is a Board4_3",email, "Your Auth Serial Code is "+result);

        log.info("email={}", result);

        return result;
    }
    /**
     * test
     */
}
