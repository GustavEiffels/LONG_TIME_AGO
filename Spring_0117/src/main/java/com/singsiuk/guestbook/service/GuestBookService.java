package com.singsiuk.guestbook.service;

import com.singsiuk.guestbook.Entity.GuestBook;
import com.singsiuk.guestbook.dto.GuestBookDto;
import com.singsiuk.guestbook.dto.PageRequestDto;
import com.singsiuk.guestbook.dto.PageResponseDto;

public interface GuestBookService {
    //DTO 클래스의 인스턴스를 Entity 인스턴스로 변환해주는 method
    default GuestBook dtoToEntity(GuestBookDto dto){
    GuestBook entity = GuestBook.builder()
            .gno(dto.getGno())
            .title(dto.getTitle())
            .content(dto.getContent())
            .writer(dto.getWriter())
            .build();
    return entity;
    }
    // Entity 클래스의 Instance를 DTO 클래스의 Instance로 변환해 주는 method
    default GuestBookDto entityDto(GuestBook guestBook){
        GuestBookDto dto= GuestBookDto.builder()
                .gno(guestBook.getGno())
                .title(guestBook.getTitle())
                .content(guestBook.getContent())
                .writer(guestBook.getWriter())
                .regDate(guestBook.getRegDate())
                .modDate(guestBook.getModDate())
                .build();
        return dto;
    }

    // 데이터 삽입을 위한 method
    public Long register(GuestBookDto dto);


    // 데이터 목록보기를 위한 method
    public PageResponseDto<GuestBookDto,GuestBook>
            getList(PageRequestDto requestDto);


    // 상세보기를 위한 method
    public GuestBookDto read(Long gno);

    // 데이터 수정을 위한 method
    public void modify(GuestBookDto dto);

    // 데이터 삭제를 위한 method
    public void remove(Long gno);
}
