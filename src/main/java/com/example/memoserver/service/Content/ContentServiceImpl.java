package com.example.memoserver.service.Content;

import com.example.memoserver.dto.ContentDto;
import com.example.memoserver.entity.Board;
import com.example.memoserver.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService
{

    private final ContentRepository contentRepository;

    /** 게시글을 저장하기 위한 method */
    /** WriteFragment */
    @Override
    public void saveContent(ContentDto dto)
    {
     contentRepository.save(dtoToEntity(dto));
    }


    /** 게시글을 읽기 위한 Method  */
    /** readFragment */
    @Override
    public JSONObject getContentInfo(Long content_idx)
    {
        JSONObject obj = new JSONObject();

        Object result = contentRepository.getContentInfo(content_idx);

        Object[] resultArr = (Object[]) result;

        obj.put("content_subject",resultArr[0]);
        obj.put("content_nick_name",resultArr[1]);
        obj.put("content_write_date",resultArr[2]);
        obj.put("content_text",resultArr[3]);
        obj.put("content_image",resultArr[4]);
        obj.put("content_image_url",resultArr[5]);
        obj.put("content_writer_idx",resultArr[6]);
        obj.put("content_board_idx",resultArr[7]);

        log.info("content_image_url = {} ",resultArr[5]);

        log.info("content_subject = {}",resultArr[0]);


        return obj;
    }

    /** 게시글 가져오기 위한 method  */
    /** BoardFragment. MainFragment ----> bring */
    @Override
    public JSONArray getContentByBoard(Long content_board_idx,  int limit)
    {

        Board board = Board.builder()
                .board_idx(content_board_idx)
                .build();

        List<Object> result = null;


        JSONArray array = new JSONArray();

        limit = (limit - 1);
        System.out.println(limit);

        PageRequest pageRequest = PageRequest.of(limit,3, Sort.Direction.DESC,"content_idx");

        log.info("limit = {}", limit);
        System.out.println(limit);



        if(content_board_idx==0)
        {
            result = contentRepository.getContentByContent_board_idx_T(pageRequest,"available");
            System.out.println("test");
        }
        else
        {
            result = contentRepository.getContentByContent_board_idx(board, pageRequest,"available");
        }

        for(Object r:result)
        {
            JSONObject json = new JSONObject();
            Object[] rArr = (Object[]) r;

            json.put("content_idx",rArr[0]);
            json.put("content_nick_name",rArr[1]);
            json.put("content_write_date",rArr[2]);
            json.put("content_subject",rArr[3]);
            json.put("content_image_url",rArr[4]);
            json.put("content_image",rArr[5]);
            json.put("content_board_idx",rArr[6]);
            json.put("content_text",rArr[7]);

            array.put(json);
        }

        return array;
    }


    /** 사용자가 작성한 게시글 가져오기  */
    /** SettingFragment -----> personal*/
    @Override
    public JSONArray getPrivateUserContent(Long user_idx)
    {
        List<Object> result = null;


        JSONArray array = new JSONArray();

        result = contentRepository.getPrivateUserContent(user_idx);

        for(Object info:result)
        {
            JSONObject json = new JSONObject();
            Object[] rArr = (Object[]) info;


            json.put("content_idx",rArr[0]);
            json.put("content_write_date",rArr[1]);
            json.put("content_subject",rArr[2]);

            array.put(json);
        }
        return array;
    }
}
