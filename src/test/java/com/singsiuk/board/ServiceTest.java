package com.singsiuk.board;

import com.singsiuk.board.dto.BoardDTO;
import com.singsiuk.board.dto.PageRequestDTO;
import com.singsiuk.board.dto.PageResultDTO;
import com.singsiuk.board.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServiceTest {
    @Autowired
    private BoardService boardService;

//    @Test
    public void testInsert(){
        BoardDTO dto = BoardDTO.builder()
                .title("insert TEst")
                .content("Testing Insert Function")
                .writerEmail("user1@gmail.com")
                .build();

        Long bno = boardService.register(dto);
        System.out.println("삽입한 글 번호 : "+bno);
    }

//    @Test
    public void testList(){
        PageRequestDTO dto = new PageRequestDTO();
        PageResultDTO<BoardDTO,Object[]>result =
                    boardService.getList(dto);
        for(BoardDTO boardDTO: result.getDtoList()){
            System.out.println(boardDTO);
        }
        System.out.println(result.getPageList());
    }
//    @Test
    public void testGet(){
        BoardDTO dto = boardService.get(100L);
        System.out.println(dto);
    }

//    @Test
    public void testDelete(){
        boardService.removeWithReplies(90L);
    }


//    @Test
    public void testModify(){
        BoardDTO dto = BoardDTO.builder()
                .bon(100L)
                .title("Modified Titile")
                .content("Modified Contents")
                .build();
        boardService.modify(dto);
    }
}
