package com.example.memoserver.service.DupCheck;

public interface DupCheckService
{
    boolean idDuplicateCheck(String userId);

    boolean nickDuplicateCheck(String userNick);
}
