package com.singsiuk.guestbook.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

// 출력할 내용을 가지는 DTO
@Data
// DTO는 출력할 DTO 클래스이고 EN 은 Entity Class
public class PageResponseDto<DTO, EN> {
    // 목록보기 이기 때문에 당연히 DTO의 List 가 존재 해야한다.

    // DTO의 List
    private List<DTO> dtoList;

    // 전체 page 개수
    private int totalPage;

    // 현재 page 번호
    private int page;

    //출력할 page 번호
    private int size;

    // 시작하는 page 번호
    private int start;

    // 끝나는 page 번호
    private int end;

    //이전 다음 링크 여부
    private boolean prev;
    private boolean next;

    // page 번호 목록
    private List<Integer> pageList;

    // page 번호 목록 만드는 method
    private void makePageList(Pageable pageable){
        this.page=pageable.getPageNumber()+1;
        this.size=pageable.getPageSize();


        //시작하는 page 번호 계산
        // 10은 출력할 page 번호 개수
        // 9는 page 번호 개수 -1
        int tempEnd = (int)(Math.ceil((page/10.0))*10);
        start=tempEnd-9;

        prev=start>1;
        end=totalPage>tempEnd?tempEnd:totalPage;

        next=totalPage>tempEnd;

        pageList = new ArrayList<>();
        for(int i=start;i<=end; i=i+1){
            pageList.add(i);
        }
    }

    // page의 내용을 가지고 dtoList로 만들어주는 method
    public PageResponseDto(Page<EN> result, Function<EN,DTO>fn){
        //Stream : 순회
        // Map : 변환
        // collect Collection을 생성해주는 method
        dtoList=result.stream().map(fn).collect(Collectors.toList());

        // 전체 page 개수 가져오기
        totalPage=result.getTotalPages();
        makePageList(result.getPageable());
    }
}
