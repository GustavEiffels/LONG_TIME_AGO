package com.singsiuk.board.controller;

import com.singsiuk.board.dto.ReplyDTO;
import com.singsiuk.board.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Log4j2
@RequestMapping("/replies/")
public class ReplyController {

    private final ReplyService replyService;

    // 댓글 목록 요청을 처리하는 Method
    @GetMapping(value="/board/{bon}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReplyDTO>> getListByBon(
            @PathVariable("bon")Long bon){
        return  new ResponseEntity<>(
                replyService.getList(bon), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Long> register(@RequestBody ReplyDTO replyDTO){
        Long rno = replyService.register(replyDTO);
        return new ResponseEntity<>(rno, HttpStatus.OK);
    }
    // 댓글 삭제 요청을 처리하는 method
    @DeleteMapping("/{rno}")
    public ResponseEntity<String> remove(@PathVariable("rno") Long rno){
        replyService.remove(rno);
        return new ResponseEntity<>("success",HttpStatus.OK);
    }

    @PutMapping("/{rno}")
    public ResponseEntity<String> modify(@RequestBody ReplyDTO replyDTO){
        replyService.modify(replyDTO);
        return new ResponseEntity<>("success",HttpStatus.OK);
    }
}
