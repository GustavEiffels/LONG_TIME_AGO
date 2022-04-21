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



    @PostMapping("/getContent")
    public String getContent(Long read_content_idx)
    {
        return contentService.getContentInfo(read_content_idx).toString();
    }

    @PatchMapping("/modify")
    public String modify(HttpServletRequest request) throws IOException {


        return modifyService.updateContent(request,uploadPath);
    }


    @PatchMapping("changePw")
    public void updatePw(String newPw, Long user_idx)
    {
        modifyService.changePw(0, newPw, user_idx);
    }
}
