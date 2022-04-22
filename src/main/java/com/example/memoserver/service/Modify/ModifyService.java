package com.example.memoserver.service.Modify;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface ModifyService
{
    String updateContent(HttpServletRequest request, String uploadPath) throws IOException;

    // change pw
    int changePw(int userAuto, String newPw, Long userIdx);


    // get User Pw
    String getUserPassword(Long user_idx);


    int resignUser(Long idx);

    /** 유저 계정 복구 하는 method : RestoreFragment ---------------> restore: restore*/
    void restore(String email) throws MessagingException;
}
