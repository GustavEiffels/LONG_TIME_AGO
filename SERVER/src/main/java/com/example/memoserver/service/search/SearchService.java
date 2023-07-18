package com.example.memoserver.service.search;

import org.json.JSONArray;

public interface SearchService
{
    JSONArray searchResult(String query, int limit);
}
