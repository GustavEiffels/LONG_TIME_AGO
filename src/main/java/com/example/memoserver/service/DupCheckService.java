package com.example.memoserver.service;

public interface DupCheckService
{
    boolean idDuplicateCheck(String userId);

    boolean nickDuplicateCheck(String userNick);
}
