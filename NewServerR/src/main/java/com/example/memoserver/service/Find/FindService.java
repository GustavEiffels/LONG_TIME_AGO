package com.example.memoserver.service.Find;

import javax.mail.MessagingException;

public interface FindService
{
    /** 자신의 계정을 찾는 Method */
    /** FindAccountFragment */
    String findAccount(String email) throws MessagingException;

    String findPassword(String email, String id) throws MessagingException;
}
