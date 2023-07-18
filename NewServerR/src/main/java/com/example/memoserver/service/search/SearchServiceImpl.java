package com.example.memoserver.service.search;
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
@RequiredArgsConstructor
@Slf4j
public class SearchServiceImpl implements SearchService
{
    private  final ContentRepository contentRepository;

    @Override
    public JSONArray searchResult(String query, int limit)
    {
        List<Object> result = null;


        JSONArray array = new JSONArray();

        PageRequest pageRequest = PageRequest.of(limit-1, 10 , Sort.Direction.DESC, "content_idx");

        result = contentRepository.getContentBySearch(query,pageRequest,"available");

        for(Object info:result)
        {
            JSONObject json = new JSONObject();
            Object[] rArr = (Object[]) info;

            json.put("content_idx",rArr[0]);
            json.put("content_nick_name",rArr[1]);
            json.put("content_write_date",rArr[2]);
            json.put("content_subject",rArr[3]);
            json.put("content_image_url",rArr[4]);
            json.put("content_image",rArr[5]);
            json.put("content_board_idx",rArr[6]);

            array.put(json);
        }
        return array;
    }
}
