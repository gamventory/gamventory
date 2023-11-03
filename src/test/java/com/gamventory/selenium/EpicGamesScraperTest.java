package com.gamventory.selenium;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.gamventory.dto.ItemFormDto;
import com.gamventory.dto.ItemImgDto;
import com.gamventory.service.EpicGamesScraper;
import com.gamventory.service.FileService;
import com.gamventory.service.ItemService;
import com.gamventory.service.NintendoGamesScraper;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@SpringBootTest
public class EpicGamesScraperTest {

    @Autowired
    private EpicGamesScraper epicGamesScraper;
    
    @Autowired
    private NintendoGamesScraper nintendoGamesScraper;


    @Autowired
    private ItemService itemService;

     @Autowired
    private FileService fileService;


    //에픽게임즈 웹 크롤링
    @Test
    public void testScrapeAndSave() throws Exception {
        // 스크레이핑한 아이템 목록을 가져옵니다.
        List<ItemFormDto> scrapedItems = epicGamesScraper.scrapeEpicGames();

        for (ItemFormDto item : scrapedItems) {
            // 아이템 이미지 리스트를 생성합니다.
            List<MultipartFile> itemImgFileList = new ArrayList<>();
            for (ItemImgDto imgDto : item.getItemImgDtoList()) {
                Path imagePath = Path.of(imgDto.getOriImgName());
                String imageName = imagePath.getFileName().toString();
                String contentType = "image/jpeg"; // 이미지의 MIME 타입 설정
                byte[] content = Files.readAllBytes(imagePath);
                MultipartFile multipartFile = new MockMultipartFile(imageName, imageName, contentType, content);
                itemImgFileList.add(multipartFile);
            }

            // ItemService의 saveItem 메서드를 사용하여 아이템과 이미지를 저장합니다.
            itemService.saveItem(item, itemImgFileList);
        }
    }

    //닌텐도 웹크롤링
    @Test
    public void testScrapeAndSaveNintendo() throws Exception {
        // 스크레이핑한 아이템 목록을 가져옵니다.
        List<ItemFormDto> scrapedItems = nintendoGamesScraper.scrapeNintendoGames();

        for (ItemFormDto item : scrapedItems) {
            // 아이템 이미지 리스트를 생성합니다.
            List<MultipartFile> itemImgFileList = new ArrayList<>();
            for (ItemImgDto imgDto : item.getItemImgDtoList()) {
                Path imagePath = Path.of(imgDto.getOriImgName());
                String imageName = imagePath.getFileName().toString();
                String contentType = "image/jpeg"; // 이미지의 MIME 타입 설정
                byte[] content = Files.readAllBytes(imagePath);
                MultipartFile multipartFile = new MockMultipartFile(imageName, imageName, contentType, content);
                itemImgFileList.add(multipartFile);
            }

            // ItemService의 saveItem 메서드를 사용하여 아이템과 이미지를 저장합니다.
            itemService.saveItem(item, itemImgFileList);
        }
    }
}
