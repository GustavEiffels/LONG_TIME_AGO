package com.example.memoserver.controller;

import com.example.memoserver.service.search.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/search")
public class SearchController
{
    @Autowired
    private SearchService searchService;

    @PostMapping("")
    public String searchResult(String query, int limit)
    {
        log.info("query = {}",query);
        return searchService.searchResult(query, limit).toString();

    }
}
