package com.example.memoserver.service.Content;


import com.example.memoserver.dto.ContentDto;
import com.example.memoserver.entity.Board;
import com.example.memoserver.entity.Content;
import com.example.memoserver.entity.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;

public interface ContentService
{
    /** dto 를 Entity 로 변환하는 method */
    default Content dtoToEntity(ContentDto dto)
    {
        User user  = User.builder()
                .user_idx(dto.getContent_writer_idx())
                .build();

        Board board = Board.builder()
                .board_idx(dto.getContent_board_idx())
                .build();

        Content content = Content.builder()
                .content_writer_idx(user)
                .content_board_idx(board)
                .content_text(dto.getContent_text())
                .content_subject(dto.getContent_subject())
                .content_image(dto.getContent_image())
                .content_write_date(LocalDateTime.now())
                .content_image_url(dto.getContent_image_url())
                .build();
        return content;
    }


    /** Entity 를 Dto 로 변환하는 method */
    default ContentDto entityToDto(User user, Board board, Content content)
    {

        ContentDto contentDto = ContentDto.builder()
                .content_board_idx(board.getBoard_idx())
                .content_image(content.getContent_image())
                .content_text(content.getContent_text())
                .content_subject(content.getContent_subject())
                .content_writer_idx(user.getUser_idx())
                .content_write_date(content.getContent_write_date())
                .content_image_url(content.getContent_image_url())
                .build();
        return contentDto;
    }


    /** 게시글을 저장하기 위한 method */
    /** WriteFragment */
    void saveContent(ContentDto dto);


    /** 게시글을 읽기 위한 Method ------------------------------- */
    /** readFragment */
    JSONObject getContentInfo(Long content_board_idx);


    /** 게시글 가져오기 위한 method ----------------------------- */
    /** BoardFragment. MainFragment ----> bring: bringContent */
    JSONArray getContentByBoard(Long content_board_idx, int limit);


    /** 사용자가 작성한 게시글 가져오기  */
    /** SettingFragment -----> personal*/
    JSONArray getPrivateUserContent(Long user_idx);
}
