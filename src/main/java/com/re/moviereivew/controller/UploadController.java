package com.re.moviereivew.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
@Log4j2
public class UploadController {
    @Value("${com.re.moviereivew.upload.path}")
    private String uploadPath;


    //디렉토리를 만드는 메서드
    private String makeDirectory(){
        //현재 날짜만 추출
        String str = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyyMMdd"));
        // / 를 파일 구분자로 변경
        String realUploadPath = str.substring(0,4) + File.separator +
                str.substring(4, 6) + File.separator + str.substring(6) ;
        //File 객체로 생성
        File uploadPathDir = new File(uploadPath, realUploadPath);
        //File 객체가 없으면 디렉토리를 생성
        if(uploadPathDir.exists() == false){
            uploadPathDir.mkdirs();
        }
        //업로드 디렉토리 경로를 리턴
        return realUploadPath;

    }

    // 파일 업로드 처리 method
    // 파일 upload는 Post Mapping 이다
    @PostMapping(value="/uploadajax")
    public void uploadFile(MultipartFile [] uploadFiles){
        for(MultipartFile uploadFile : uploadFiles) {
            //이미지 파일이 아니면 업로드 안함
            if(uploadFile.getContentType().startsWith("image") == false){
                return;
            }
            //업로드 되는 원본 파일 이름 출력
            String originalName = uploadFile.getOriginalFilename();
            //IE 나 Edge는 파일의 경로도 전달되므로 파일의 경로를 제거
            String fileName = originalName.substring(
                    originalName.lastIndexOf("\\") + 1);

            //업로드 될 디렉토리 경로를 생성
            String realUploadPath = makeDirectory();

            //uuid 생성
            String uuid = UUID.randomUUID().toString();
            //저장할 경로 생성
            String saveName =
                    uploadPath + File.separator + realUploadPath +
                            File.separator + uuid + fileName;
            Path savePath = Paths.get(saveName);
            try{
                //파일 업로드
                uploadFile.transferTo(savePath);
            }catch(Exception e){
                log.info("예외:" + e.getLocalizedMessage());
                e.printStackTrace();
            }


        }

    }

}
