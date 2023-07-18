package com.example.memoserver.service.Find;

public interface FindService
{
    String findAccount(String email);

    String findPassword(String email, String id);
}
