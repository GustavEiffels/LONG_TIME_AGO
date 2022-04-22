package com.example.memoserver.service.Modify;

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
}
