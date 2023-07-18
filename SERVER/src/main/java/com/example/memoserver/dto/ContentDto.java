package com.example.memoserver.dto;

import com.example.memoserver.entity.Board;
import com.example.memoserver.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentDto
{

    private Long content_idx;

    private String content_subject;

    private LocalDateTime content_write_date;


    private String content_text;


    private String content_image;

    private String content_image_url;


    private Long content_writer_idx;

    private Long content_board_idx;
}
