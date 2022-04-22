package com.example.memoserver.service.Modify;

import com.example.memoserver.entity.Board;
import com.example.memoserver.repository.ContentRepository;
import com.example.memoserver.repository.UserRepository;
import com.example.memoserver.service.Content.ContentService;
import com.example.memoserver.service.Email.EmailSenderService;
import com.example.memoserver.service.Email.ForFindPw;
import com.example.memoserver.service.File.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;


@Service
@Slf4j
@RequiredArgsConstructor
public class ModifyServiceImpl implements ModifyService
{

    private final ContentRepository contentRepository;


    private final FileService fileService;

    private final UserRepository userRepository;

    private final EmailSenderService emailSenderService;

    private final ForFindPw forFindPw;



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
        else if( change.equals("not") )
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
        return  request.getParameter("content_idx");
    }

    @Override
    public int changePw(int userAuto, String newPw, Long userIdx)
    {
        return userRepository.updatePw(0, newPw, userIdx);
    }

    @Override
    public String getUserPassword(Long user_idx)
    {
        return userRepository.getUserPassword(user_idx);
    }

    @Override
    public int resignUser(Long idx)
    {
        return userRepository.resignUser("NotAvailable",idx);
    }



    /** 유저 계정 복구 하는 method : RestoreFragment ---------------> restore: restore*/
    @Override
    public void restore(String email) throws MessagingException
    {
        // 새로운 비밀번호 생성
        String newPw = forFindPw.excuteGenerate();

        // 복구하려는 계정 정보 변경
        userRepository.restore(email, "available", newPw);

        // 변경된 비밀번호 email 로 전송
        emailSenderService.sendMail("Hello this is Board4_3",email, String.format("Your New Password is :{}", newPw));
    }


}
