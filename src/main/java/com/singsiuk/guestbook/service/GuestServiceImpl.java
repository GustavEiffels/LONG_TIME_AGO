package com.singsiuk.guestbook.service;


import com.querydsl.core.BooleanBuilder;
import com.singsiuk.guestbook.Entity.GuestBook;
import com.singsiuk.guestbook.Entity.QGuestBook;
import com.singsiuk.guestbook.dto.GuestBookDto;
import com.singsiuk.guestbook.dto.PageRequestDto;
import com.singsiuk.guestbook.dto.PageResponseDto;
import com.singsiuk.guestbook.repository.GuestBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestBookService{
    // 자동 주입 받기 위해서 final 로 선언해야 한다 .
    private final GuestBookRepository repository;

    @Override
    public Long register(GuestBookDto dto) {
        log.info(dto);
                // Dto를 Entity 로 변환
        GuestBook entity = dtoToEntity(dto);
        log.info(entity);
        // 데이터 삽입
        repository.save(entity);
        // 삽입한 데이터의 gno 리턴
        return entity.getGno();
        }

    @Override
    public PageResponseDto<GuestBookDto, GuestBook> getList(PageRequestDto requestDto) {
        // Pageable 객체 생성
        Pageable pageable = requestDto.getPageable(
                Sort.by("gno").descending());
        // 결과를 가져오기
//        Page<GuestBook> result = repository.findAll(pageable);
        BooleanBuilder booleanBuilder = getSearch(requestDto);
        Page<GuestBook> result = repository.findAll(booleanBuilder,pageable);

        //Function 생성
        Function<GuestBook,GuestBookDto> fn
                // entity 를 dto로 바꾸는 요청
                =(entity ->entityDto(entity));
        return new PageResponseDto<>(result, fn);
    }

    @Override
    public GuestBookDto read(Long gno) {
        // return type 이 Optional
        Optional<GuestBook> guestbook= repository.findById(gno);
         return guestbook.isPresent()?entityDto(guestbook.get()):null;
    }

    @Override
    public void modify(GuestBookDto dto) {
        // 수정할 데이터 찾아오기
        // 굳이 데이터가 없는 것을 실행할 필요는 없기 때문에
        Optional<GuestBook> result = repository.findById(dto.getGno());
        if(result.isPresent()){
            GuestBook entity =result.get();
            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());
            repository.save(entity);
        }
    }

    @Override
    public void remove(Long gno) {
        // 삭제할 데이터를 찾아오기
        Optional<GuestBook> result = repository.findById(gno);
        if(result.isPresent()){
            repository.deleteById(gno);
        }
    }
    private BooleanBuilder getSearch(PageRequestDto requestDto){
        String type= requestDto.getType();
        String keyword = requestDto.getKeyword();
        BooleanBuilder booleanBuilder= new BooleanBuilder();
        QGuestBook qGuestBook = QGuestBook.guestBook;


        if(type==null||type.trim().length()==0){
            // 좌우 공백 제거
            return booleanBuilder;
        }
        // 나머지 조건들 나열
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        // t c w 는 검색 화면에서 select의 option들의 value가 되어야한다.
        if(type.contains("t")){
            conditionBuilder.or(qGuestBook.title.contains(keyword));
        }
        if(type.contains("c")) {
            conditionBuilder.or(qGuestBook.content.contains(keyword));
        }
        if(type.contains("w")) {
            conditionBuilder.or(qGuestBook.writer.contains(keyword));
        }
        //모든 조건 통합
        booleanBuilder.and(conditionBuilder);
        return booleanBuilder;
    }
}
