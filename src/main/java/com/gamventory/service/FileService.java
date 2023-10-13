package com.gamventory.service;


import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;


@Service
@Log4j2
public class FileService {

    /* 파일 업로드 관련 로직을 작성한 파일 */
    
     public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception{
        UUID uuid = UUID.randomUUID();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        //uuid + 원래파일 이름의 확장자를 추가
        String savedFileName = uuid.toString() + extension; 
        // 업로드 경로 / uuid+ 확장자
        String fileUploadFullUrl = uploadPath + "/" + savedFileName; 
        //바이트 단위의 출력을 내보내는 클래스로 파일 출력 스트림 생성
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        //파일 스트림에 파일 데이터를 입력 
        fos.write(fileData); 
        fos.close();
        //업로드 된 파일 이름 반환
        return savedFileName; 
    }

    public void deleteFile(String filePath) throws Exception{
         //파일 객체 생성
        File deleteFile = new File(filePath);
        if(deleteFile.exists()) {
            deleteFile.delete();
            log.info("파일을 삭제하였습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }
}
