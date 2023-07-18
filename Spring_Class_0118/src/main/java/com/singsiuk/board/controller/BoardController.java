package com.singsiuk.board.controller;

import com.singsiuk.board.dto.BoardDTO;
import com.singsiuk.board.dto.PageRequestDTO;
import com.singsiuk.board.entity.Board;
import com.singsiuk.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequiredArgsConstructor
// 공통 URL 설정
@RequestMapping("/board/")
public class BoardController {
    private final BoardService boardService;

//    @Mapping("")
//    public String 이름(Parameter)
//        return View 이름
    // 데이터를 조회하는 경우 보통 Get 으로 받음
    // 목록 보기 Parameter = PageRequestDTo 였다
    @GetMapping("list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        model.addAttribute(
                "result",
                boardService.getList(pageRequestDTO)
        );
    }

    @GetMapping("register")
    public void register(){
    }

    @PostMapping("register")
    // Model 은 삽입 삭제 수정에서 사용하면 안됨
    // Model 대신 RedirectAttritbutes 를 사용
    public String register(BoardDTO dto, RedirectAttributes rattr){
        Long bon = boardService.register(dto);
        rattr.addFlashAttribute("msg",bon+"Register");
        return "redirect:/board/list";
    }

    @GetMapping({"read","modify"})
    // 여기서 ModelAttribute를 작성한 Parameter는 아무런 작업을 하지 않아도
    // view 로 전달
    public void read(@ModelAttribute("requestDTO")PageRequestDTO pageRequestDTO,
                     Long bon,
                     Model model){
        BoardDTO dto = boardService.get(bon);
        model.addAttribute("dto",dto);
    }

    @PostMapping("remove")
    public String remove(long bon, RedirectAttributes rattr){
        boardService.removeWithReplies(bon);
        // 출력할 message 를 저장
        rattr.addFlashAttribute("msg",bon+"삭제!! Good Bye");

        return"redirect:/board/list";
    }

    @PostMapping("modify")
    // 수정하려면 4가지 데이터가 필요하다 .
    public String modify(
            BoardDTO dto,
            @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
            RedirectAttributes rattr){
        boardService.modify(dto);
        rattr.addAttribute("page",requestDTO.getPage());
        rattr.addAttribute("type",requestDTO.getType());
        rattr.addAttribute("keyword",requestDTO.getKeyword());
        rattr.addAttribute("bon",dto.getBon());
        return "redirect:/board/read";
    }
}
