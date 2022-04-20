package com.example.memoserver.service.Modify;

import com.example.memoserver.entity.Board;
import com.example.memoserver.repository.ContentRepository;
import com.example.memoserver.service.Content.ContentService;
import com.example.memoserver.service.File.FileService;
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

    private final FileService fileService;



    @Override
    public String updateContent(HttpServletRequest request, String uploadPath) throws IOException {
        MultipartHttpServletRequest multi = (MultipartHttpServletRequest) request;
        MultipartFile file = multi.getFile("content_image");

        String delete = request.getParameter("Delete");
        String change = request.getParameter("Change");

        log.info("delete ={}", delete);
        log.info("change ={}", change);

        Board board = Board.builder()
                .board_idx(Long.valueOf(request.getParameter("content_board_idx")))
                .build();

        if( delete.equals("delete"))
        {
            contentRepository.contentUpdate(
                    request.getParameter("content_subject"),
                    request.getParameter("content_text"),
                    "empty",
                    "empty",
                    board,
                    Long.valueOf(request.getParameter("content_idx"))
            );
        }
        else if( change.equals("change") )
        {
            String url = fileService.transToImage(file, uploadPath);
            contentRepository.contentUpdate(
                    request.getParameter("content_subject"),
                    request.getParameter("content_text"),
                    file.getOriginalFilename(),
                    url,
                    board,
                    Long.valueOf(request.getParameter("content_idx")));
        }
        else if( change.equals("not"))
        {
            contentRepository.contentUpdateNoImage(
                    request.getParameter("content_subject"),
                    request.getParameter("content_text"),
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
