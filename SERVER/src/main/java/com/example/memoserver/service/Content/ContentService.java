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

    void saveContent(ContentDto dto);


    JSONObject getContentInfo(Long content_board_idx);

    JSONArray getContentByBoard(Long content_board_idx, int limit);

    JSONArray getPrivateUserContent(Long user_idx);
}
