package com.singsiuk.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {
    private int page;
    private int size;
    private String type;
    private String keyword;

    // 기본 생성자를 이용해서 Page 와 Size 값 초기화

    // 하나도 값을주지 않았을때 생성자를 생성
    public PageRequestDTO(){
        page = 1 ;
        size = 10;
    }

    // Pageable 객체 생성 해주는 method
    public Pageable getPageable(Sort sort){
        // Page 는 0 번부터 시작
        return PageRequest.of(page -1,size,sort);

    }
}
