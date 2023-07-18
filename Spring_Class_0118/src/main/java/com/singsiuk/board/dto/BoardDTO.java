package com.singsiuk.board.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
// Entity 는 보통 Getter 만 만듬
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    // 게시판 글 번호
    private Long bon;

    // 게시판 글 제목
    private String title;

    // 게시판 글 내용
    private String content;

    // 작성자 Email
    private String writerEmail;

    // 작성자 이름
    private String writerName;

    // 게시글 등록 날짜
    private LocalDateTime regDate;

    // 게시글 수정 날짜
    private LocalDateTime modDate;

    // 해당 게시글의 댓글 수
    private int replyCount;

}
