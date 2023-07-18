package com.example.memoserver.service.File;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService
{
    String transToImage(MultipartFile file, String uploadPath) throws IOException;

    String makeFolder(String uploadPath);


}
