package com.example.memoserver;

import com.example.memoserver.controller.SearchController;
import com.example.memoserver.entity.Board;
import com.example.memoserver.entity.Content;
import com.example.memoserver.entity.User;
import com.example.memoserver.repository.ContentRepository;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;

@SpringBootTest
class MemoServerApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private  ContentRepository contentRepository;

    @Test
    public void contentRepositoryTest()
    {
        Object result = contentRepository.getContentInfo(30L);
        Object[] resultArr = (Object[]) result;
        System.out.println((String)resultArr[0]);

    }

    @Test
    public void pathTest() throws IOException {
        File file = new File("/Users/mac/Desktop/Desktop-SungMak/00And/memoServer/src/main/resources/static/upload/2022/04/12/99f1dd98-b77f-4c73-b9ac-90011e3f3e9dtemp_1649730930786.jpg");

        byte[] fileContent = Files.readAllBytes(file.toPath());
        System.out.println(fileContent);
    }

    @Test
    public void hundredSave()
    {

        for(int i = 0 ; i < 101 ; i++) {
            User user = User.builder()
                    .user_idx(2L)
                    .build();

            Board board = Board.builder()
                    .board_idx(2L)
                    .build();

            Content content = Content.builder()
                    .content_subject("test subject" + i)
                    .content_text("this sample text numbder ={}"+i)
                    .content_writer_idx(user)
                    .content_write_date(LocalDateTime.now())
                    .content_image("")
                    .content_image_url("")
                    .content_board_idx(board)
                    .build();
            contentRepository.save(content);
        }
    }

    @Autowired
    private SearchController searchController;

//    @Test
//    public void searchController() throws ParseException {
//
//
//    }




}
