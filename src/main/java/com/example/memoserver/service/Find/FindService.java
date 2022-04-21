package com.example.memoserver.service.Find;

import javax.mail.MessagingException;

public interface FindService
{
    String findAccount(String email) throws MessagingException;

    String findPassword(String email, String id) throws MessagingException;
}
