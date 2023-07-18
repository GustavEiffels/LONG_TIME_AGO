package com.singsiuk.board.service;

import com.singsiuk.board.dto.BoardDTO;
import com.singsiuk.board.dto.PageRequestDTO;
import com.singsiuk.board.dto.PageResultDTO;
import com.singsiuk.board.entity.Board;
import com.singsiuk.board.entity.Member;

public interface BoardService {

    //게시글을 삭제하는 method
    public void modify(BoardDTO dto);

    // 게시물 등록을 위한 method
    // 등록을 위해서 기본키를 사용할 거
    // Entity 에서 글번호가 기본키이고 Type 이 Long
    public Long register(BoardDTO dto);


    // 목록 보기 요청을 처리할 method
    public PageResultDTO<BoardDTO,Object[]> getList(PageRequestDTO dto);


    // 상세보기 요청을 처리할 method
    public BoardDTO get(Long bon);

    // 게시글을 삭제하는 method
    public void removeWithReplies(Long bon);


    //Board DTO 를 Board Entity 로 변환해 주는 Method
    default Board dtoToEntity(BoardDTO dto){
        // Entity 에 Member 가 있어서 생성
        Member member = Member.builder()
                .email(dto.getWriterEmail())
                .build();
        Board board = Board.builder()
                .bon(dto.getBon())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();
        return board;
        // 해당의 것은 Service Impl 에 만들어도 됨
        // ServiceImpl 에는 Business Logic 만 기재하기 위해서
        // 결국
        // 이 method 는 Interface 에서 선언하고 클래스에 구현해도 되고
        // Interface에 만든 이유는 class 에는 실제 비지니스 로직에 관련된
        // method 만 존재하게 하고 싶어서 이다
        // 이러한 method 를 별도의 클래스에 static method 로 만들어두어도 된다
        // 이 경우 Class 이름에는 Wrapper 를 붙이는 것이 좋다
   }

   // board Entity를 Board DTO Class 로 변환하는 method
    // 위의 method 와 방향이 반대
    default BoardDTO entityToDTO(Board board,
                                 Member member,
                                 Long replyCount){
        BoardDTO dto = BoardDTO.builder()
                .bon(board.getBon())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                //Writer Email 과 Name 은 Entity 에 존재하지 않아서
                // Member 를 받아야함
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue())
                .build();
        return dto;
    }
}
