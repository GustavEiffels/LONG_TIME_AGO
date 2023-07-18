package com.example.memoserver.controller;

import com.example.memoserver.service.Content.ContentService;
import com.example.memoserver.service.Login.LoginService;
import com.example.memoserver.service.Modify.ModifyService;
import com.example.memoserver.service.User.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;;import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/modify")
public class ModifyController
{
    @Autowired
    private ContentService contentService;

    @Autowired
    private ModifyService modifyService;

    @Value("${uploadlocation}")
    private String uploadPath;


    //------------------------------------------------------------------------------------------------------------------



    @PostMapping("/getContent")
    public String getContent(Long read_content_idx)
    {
        return contentService.getContentInfo(read_content_idx).toString();
    }



    /** 게시글 수정 method ------------------------------------------------------------------------------------------------
     */
    @PatchMapping("/modify")
    public String modify(HttpServletRequest request) throws IOException
    {
        return modifyService.updateContent(request,uploadPath);
    }



    /** 비밀번호 변경 -----------------------------------------------------------------------------------------------------
     *  PwChangeFragment
     */
    @PatchMapping("changePw")
    public void updatePw(String newPw, Long user_idx)
    {
        modifyService.changePw(0, newPw, user_idx);
    }



    /** 비밀번호 확인 -----------------------------------------------------------------------------------------------------
     *
     *  알맞은 비밀번호인지 확인하는 method
     *  PwCheckFragment
     */
    @PostMapping("getPassword")
    public String getUserPassword(String user_idx, String get_password)
    {
        if( !get_password.equals( modifyService.getUserPassword( Long.valueOf(user_idx) ) ) )
        {
            return "Wrong Password";
        }
        return "collect";
    }



    /** 회원 탈퇴 method -------------------------------------------------------------------------------------------------
     * ResignFragment
     */
    @PatchMapping("resign")
    public String resignUser(String idx)
    {
        String result = "N";
        if( modifyService.resignUser( Long.valueOf(idx) ) >0 )
        {
            result = "Y";
        }
        return result;
    }


    /** 유저 계정 복구 하는 method : RestoreFragment ---------------> restore: restore*/
    @PatchMapping("restore")
    public void restore(String email) throws MessagingException
    {
        modifyService.restore(email);
    }
}
