package com.example.memoserver.controller;

import com.example.memoserver.entity.Board;
import com.example.memoserver.entity.Content;
import com.example.memoserver.entity.User;
import com.example.memoserver.repository.ContentRepository;
import com.example.memoserver.service.Content.ContentService;
import com.example.memoserver.service.File.FileService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/content")
public class ContentsController
{
    @Value("${uploadlocation}")
    private String uploadPath;

    @Autowired
    private ContentService contentService;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private FileService fileService;

    // -----------------------------------------------------------------------------------------------------------------

    /** 게시글을 저장하는 method ------------------------------ */
    /** WriteFragment */
    @PostMapping(value = "")
    public void saveContent(HttpServletRequest request, HttpServletResponse response) throws IOException
    {


        MultipartHttpServletRequest multi = (MultipartHttpServletRequest) request;
        MultipartFile file = multi.getFile("content_image");


        User user  = User.builder()
                .user_idx( Long.valueOf(request.getParameter("content_writer_idx")) )
                .build();

        Board board = Board.builder()
                .board_idx( Long.valueOf(request.getParameter("content_board_idx")) )
                .build();

        // 게시글에 사진이 포함되지 않았을 때
        if( file==null )
        {
            Content content = Content.builder()
                    .content_subject(request.getParameter("content_subject"))
                    .content_text(request.getParameter("content_text"))
                    .content_writer_idx(user)
                    .content_write_date(LocalDateTime.now())
                    .content_image("empty")
                    .content_image_url("empty")
                    .content_board_idx(board)
                    .build();
            contentRepository.save(content);

        }
        // 게시글에 사진이 포함되었을 때
        else
        {
            // 저장할 파일
            byte[] saveFile = file.getBytes();

            // Random 한 UUID 생성
            String uuid = UUID.randomUUID().toString();
            String fileName = file.getOriginalFilename();
            int spot = fileName.indexOf(".");
            String saveFileName  = fileName.substring(0,spot);

            // 저장할 디렉토리 만들기
            String realUploadFolder = fileService.makeFolder(uploadPath);

            // directory 에 image 저장하기
            String imageSavePath = uploadPath+File.separator+realUploadFolder+File.separator+uuid+fileName;

            Path savePath = Paths.get(imageSavePath);

            file.transferTo(savePath);


            Content content = Content.builder()
                    .content_subject(request.getParameter("content_subject"))
                    .content_text(request.getParameter("content_text"))
                    .content_writer_idx(user)
                    .content_write_date(LocalDateTime.now())
                    .content_image(file.getOriginalFilename())
                    .content_board_idx(board)
                    .content_image_url(imageSavePath)
                    .build();

            contentRepository.save(content);
        }

        Board boar = Board.builder()
                .board_idx(Long.valueOf(request.getParameter("content_board_idx")))
                .build();

        // 제일 최근에 올라온 content_idx 를 받아온다.
        int result = contentRepository.getMaxContentIdx(boar);


        response.getWriter().write(String.valueOf(result));


    }


    /** 게시글을 읽기 위한 Method ------------------------------- */
    /** readFragment */
    @PostMapping("/read")
    public String readContentInfo(Long read_content_idx) { return contentService.getContentInfo(read_content_idx).toString();}


    /** 게시글 가져오기 위한 method ----------------------------- */
    /** BoardFragment. MainFragment ----> bring: bringContent */
    @PostMapping("/bring")
    public String bringContent(Long content_board_idx, int limit)
    {
        JSONArray result = contentService.getContentByBoard(content_board_idx, limit);
        return result.toString();
    }



    /** 사용자가 작성한 게시글 가져오기  */
    /** SettingFragment -----> personal*/
    @PostMapping("/personal")
    public String getPersonalContent(String user_idx)
    {
        Long userId = Long.valueOf(user_idx);
        JSONArray result = contentService.getPrivateUserContent(userId);
        return result.toString();
    }



    /** 게시글의 이미지가 존재할 경우 해당 이미지를 바이트 배열로 변환해서 가져오기  */
    /** ReadFragment ----> getImage */
    @PostMapping("/getImage")
    public byte[] getImage(String content_image_url) throws IOException {
        if(!content_image_url.equals("empty"))
        {
            File file = new File(content_image_url);
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return fileContent;
        }
        return null;
    }



    /** 게시글을 삭제하는 method */
    /** ReadFragment ---> delete*/
    @DeleteMapping("/delete")
    public void deleteContent(Long content_idx)
    {
        contentRepository.deleteContent(content_idx);
    }
}