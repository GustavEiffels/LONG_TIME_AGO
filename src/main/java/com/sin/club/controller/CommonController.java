package com.sin.club.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class CommonController {
    @GetMapping("/customlogin")
    public void customlogin(){
        log.info("------- Customer Login Page--------- ");
    }
}
