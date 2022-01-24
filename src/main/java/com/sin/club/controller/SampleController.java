package com.sin.club.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
// 공통 URL 설정
@RequestMapping("/sample")
public class SampleController {
    // 누구나 접속할 수 있는 page 생성
    @GetMapping("/all")
    public void exAll(){
        log.info("all user can access");
    }

    @GetMapping("/member")
    public void onlyMember(){
        log.info("Member Only");
    }
    @GetMapping("/admin")
    public void onlyAdmin(){
        log.info("Admin Only");
    }
}
