package com.singsiuk.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


@Data
// DTO 클래스와 ENTITY 클래스를 GENERIC 으로 설정
public class PageResultDTO<DTO, EN> {
    // DTO 리스트
    private List<DTO> dtoList;

    // 전체페이지, 시작페이지 , 종료 페이지
    private int totalPage, start, end;

    // 현재페이지 번호
    private int page;

    //  페이지당 데이터의 개수(몇개 들어갈건지)
    private int size;

    // 이전과 다음 여부
    private boolean prev, next;

    // 페이지 번호 목록
    private List<Integer> pageList;

    // 생성자
    // EN = Entity
    public  PageResultDTO(Page<EN> result,
                          Function<EN,DTO> fn){
        // ENTITY 로 넘어온 결과를 DTO 로 변환
        dtoList=result.stream().map(fn).collect(Collectors.toList());

        // 전체 페이지 개수 구하기
        totalPage = result.getTotalPages();

        //페이지 번호 목록 구하기
        makePageList(result.getPageable());

    }
    // 페이지 번호 목록을 만들어주는 method
    // 여기 Class 에서만 사용하기 때문에 Private
    private void makePageList(Pageable pageable){
        // 현재 페이지
        this.page = pageable.getPageNumber()+1;

        // 페이지당 출력되는 데이터 개수
        this.size = pageable.getPageSize();

        // 시작 페이지 번호와 종료페이지 번호 계산
        // 여기서 10 은 페이지 번호 출력 개수
        // 입력을 받아서 변경하고자 하면 10 을 변수로 변경하고 9 는 +1로 변경
        int tempEnd= (int)(Math.ceil(page/10.0))*10;
        start = tempEnd-9;
        prev=start >1;

        end = totalPage>tempEnd ? tempEnd:totalPage;
        next = totalPage>tempEnd;

        pageList=new ArrayList<>();
        for(int i = start; i <=end ; i++){
            pageList.add(i);
        }
    }
 }
