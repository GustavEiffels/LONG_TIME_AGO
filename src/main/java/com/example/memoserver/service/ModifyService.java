package com.example.memoserver.service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface ModifyService
{
    String updateContent(HttpServletRequest request, String uploadPath) throws IOException;
}
