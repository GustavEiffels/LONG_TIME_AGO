package com.singsiuk.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@Data
@AllArgsConstructor
public class PageRequestDto {
    // 현재 페이지 번호
    private int page;

    // 페이지당 출력 개수
    private int size;

    // Parameter가 없는 생성자 ( default constructor) 생성
    public PageRequestDto(){
        page=1;
        size=10;
    }

    // Page 와 Size 를 이용한 Pageable 객체를 생성해서 return하는 method

    public Pageable getPageable(Sort sort){
        // 페이지 번호는 -1 을 해서 생성
        // 사람은 1 부터
        // 컴퓨터는 0 부터
        return PageRequest.of(page-1,size,sort);
    }
}
