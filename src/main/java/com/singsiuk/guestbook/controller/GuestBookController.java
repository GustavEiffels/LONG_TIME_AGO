package com.singsiuk.guestbook.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 로그 추력을 위한 Annotation
@Log4j2
//PageController 를 만들기 위한 Annotation
@Controller
public class GuestBookController {
    @GetMapping("/")
    public String main(){
        // 배포시 지워진다
        log.info("시작요청");
        //Template 에 있는 Guestbook 디렉토리의 list.html 을 출력
    return "/guestbook/list";
    }
}
