package com.singsiuk.guestbook.controller;

import com.singsiuk.guestbook.dto.GuestBookDto;
import com.singsiuk.guestbook.dto.PageRequestDto;
import com.singsiuk.guestbook.dto.PageResponseDto;
import com.singsiuk.guestbook.service.GuestBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// 로그 추력을 위한 Annotation
@Log4j2
//PageController 를 만들기 위한 Annotation
@Controller
@RequiredArgsConstructor
public class GuestBookController {
    // Service 주입
    private final GuestBookService service;

    @GetMapping("/")
    public String main(){
        // 배포시 지워진다
        log.info("시작요청");
        //Template 에 있는 Guestbook 디렉토리의 list.html 을 출력

    return "redirect:/guestbook/list";
    }

    @GetMapping("/guestbook/list")
    public void list(PageRequestDto pageRequestDto, Model model){
        log.info("Show List");
        PageResponseDto result = service.getList(pageRequestDto);
        model.addAttribute("result",result);
    }

    @GetMapping("/guestbook/register")
    public void register(){
        log.info("삽입 요청 page 로 이동 ");
    }

    @PostMapping("/guestbook/register")
    public String register(GuestBookDto dto, RedirectAttributes redirectAttributes){
        log.info("Insert Request");
        // 삽입 처리
        Long gno = service.register(dto);
        // 리다이렉트 할 때 한번만 사용하는 데이터 생성
        redirectAttributes.addFlashAttribute(
                "msg", gno+"Insert");
        // 작업 후 목록보기로 redirect
        return "redierct:/guestbook/list";
    }

    @GetMapping("/guestbook/read")
    // 파라미터 중 gno는 gno에 대입
    // 나머지는 requestDto 에 대입
    // 다음과 같은 페이지를 전송
    public void read(long gno,
                     @ModelAttribute("requestDto")
                             PageRequestDto requestDto,
                                                Model model){
        GuestBookDto dto = service.read(gno);
        model.addAttribute("dto",dto);
    }
}
