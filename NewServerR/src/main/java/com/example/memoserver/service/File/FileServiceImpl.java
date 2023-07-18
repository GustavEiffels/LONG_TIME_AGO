package com.example.memoserver.service.File;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileServiceImpl implements FileService
{
    @Override
    public String transToImage(MultipartFile file, String uploadPath) throws IOException {
        // 저장할 파일
        byte[] saveFile = file.getBytes();

        // Random 한 UUID 생성
        String uuid = UUID.randomUUID().toString();
        String fileName = file.getOriginalFilename();
        int spot = fileName.indexOf(".");
        String saveFileName  = fileName.substring(0,spot);

        // 저장할 디렉토리 만들기
        String realUploadFolder = makeFolder(uploadPath);

        // directory 에 image 저장하기
        String imageSavePath = uploadPath+ File.separator+realUploadFolder+File.separator+uuid+fileName;

        Path savePath = Paths.get(imageSavePath);

        file.transferTo(savePath);

        return imageSavePath;
    }

    @Override
    public String makeFolder(String uploadPath) {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String realUploadPath =  str.replace("/", File.separator);
        File uploadPathDir = new File(uploadPath, realUploadPath);
        if (uploadPathDir.exists() == false) {
            uploadPathDir.mkdirs();
        }
        return realUploadPath;
    }
}
