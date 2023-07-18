package com.example.memoserver.service.Modify;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface ModifyService
{
    String updateContent(HttpServletRequest request, String uploadPath) throws IOException;
}
