package com.singsiuk.guestbook;

import com.singsiuk.guestbook.Entity.GuestBook;
import com.singsiuk.guestbook.dto.GuestBookDto;
import com.singsiuk.guestbook.dto.PageRequestDto;
import com.singsiuk.guestbook.dto.PageResponseDto;
import com.singsiuk.guestbook.service.GuestBookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServiceTest {
    @Autowired
    private GuestBookService service;

    @Test
    public void registerTest(){
        GuestBookDto dto = GuestBookDto.builder()
                .title("제목")
                .content("내용")
                .writer("singsiuk")
                .build();
        Long gno = service.register(dto);
        System.out.println(gno);
    }

    @Test
    public void listTest(){
        PageRequestDto pageRequestDto=
                PageRequestDto.builder()
                        .page(1)
                        .size(10)
                        .build();
        PageResponseDto<GuestBookDto, GuestBook>
                pageResponseDto=service.getList(pageRequestDto);
        for(GuestBookDto dto:pageResponseDto.getDtoList()){
            System.out.println(dto);
        }
        //이전 과 다음 링크 여부와 전체 페이지 개수 확인
        System.out.println("==================================");
        System.out.println("이전:"+pageResponseDto.isPrev());
        System.out.println("다음:"+pageResponseDto.isNext());
        System.out.println("다음:"+pageResponseDto.getTotalPage());

        // 페이지 번호 목록 추력
        System.out.println("===================================");
        for(Integer i:pageResponseDto.getPageList()){
            System.out.print(i+"\t");
        }
    }
}
