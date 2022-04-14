package com.example.memoserver.service;

import com.example.memoserver.entity.Board;
import com.example.memoserver.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;


@Service
@Slf4j
@RequiredArgsConstructor
public class ModifyServiceImpl implements ModifyService
{

    private final ContentRepository contentRepository;

    private  final ContentService contentService;

    @Override
    public String updateContent(HttpServletRequest request, String uploadPath) throws IOException {
        MultipartHttpServletRequest multi = (MultipartHttpServletRequest) request;
        MultipartFile file = multi.getFile("content_image");

        Board board = Board.builder()
                .board_idx(Long.valueOf(request.getParameter("content_board_idx")))
                .build();

        if( file==null )
        {
            contentRepository.contentUpdate(
                    request.getParameter("content_subject"),
                    request.getParameter("content_text"),
                    "",
                    "",
                    board,
                    Long.valueOf(request.getParameter("content_idx"))
            );
        }
        else
        {
            String url = contentService.transToImage(file, uploadPath);
            contentRepository.contentUpdate(
                    request.getParameter("content_subject"),
                    request.getParameter("content_text"),
                    file.getOriginalFilename(),
                    url,
                    board,
                    Long.valueOf(request.getParameter("content_idx")));
        }
        Board boar = Board.builder()
                .board_idx(Long.valueOf(request.getParameter("content_board_idx")))
                .build();


        int result = contentRepository.getMaxContentIdx(boar);

        // content_board_idx를 입력받아서 현재 가장 마지막에 생성된 content_idx 를 return 받는다
        return  String.valueOf(result);
    }
}
