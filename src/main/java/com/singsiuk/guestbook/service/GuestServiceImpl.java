package com.singsiuk.guestbook.service;


import com.singsiuk.guestbook.Entity.GuestBook;
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
        Page<GuestBook> result = repository.findAll(pageable);

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
}
