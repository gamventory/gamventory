package com.gamventory.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.multipart.MultipartFile;

import com.gamventory.constant.ItemSellStatus;
import com.gamventory.dto.ItemFormDto;
import com.gamventory.entity.Item;
import com.gamventory.entity.ItemImg;
import com.gamventory.repository.ItemImgRepository;
import com.gamventory.repository.ItemRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemImgRepository itemImgRepository;

    List<MultipartFile> createMultipartFiles() throws Exception{

        List<MultipartFile> multipartFileList = new ArrayList<>();

        for(int i=0;i<5;i++){
            String path = "C:/shop/item/";
            String imageName = "image" + i + ".jpg";
            MockMultipartFile multipartFile =
                    new MockMultipartFile(path, imageName, "image/jpg", new byte[]{1,2,3,4});
            multipartFileList.add(multipartFile);
        }

        return multipartFileList;
    }

    @Test
    @DisplayName("상품 등록 테스트")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void saveItem() throws Exception {
        ItemFormDto itemFormDto = new ItemFormDto();
        itemFormDto.setItemNm("테스트상품");
        itemFormDto.setItemSellStatus(ItemSellStatus.SELL);
        itemFormDto.setItemDetail("테스트 상품 입니다.");
        itemFormDto.setPrice(1000);
        itemFormDto.setStockNumber(100);

        List<MultipartFile> multipartFileList = createMultipartFiles();
        Long itemId = itemService.saveItem(itemFormDto, multipartFileList);
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);

        Item item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);

        assertEquals(itemFormDto.getItemNm(), item.getItemNm());
        assertEquals(itemFormDto.getItemSellStatus(), item.getItemSellStatus());
        assertEquals(itemFormDto.getItemDetail(), item.getItemDetail());
        assertEquals(itemFormDto.getPrice(), item.getPrice());
        assertEquals(itemFormDto.getStockNumber(), item.getStockNumber());
        assertEquals(multipartFileList.get(0).getOriginalFilename(), itemImgList.get(0).getOriImgName());
    }

    @Test
    @DisplayName("상품 수정 테스트")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void updateItem() throws Exception {
    // 1. 상품 등록
    ItemFormDto itemFormDto = new ItemFormDto();
    itemFormDto.setItemNm("테스트상품");
    itemFormDto.setItemSellStatus(ItemSellStatus.SELL);
    itemFormDto.setItemDetail("테스트 상품 입니다.");
    itemFormDto.setPrice(1000);
    itemFormDto.setStockNumber(100);

    List<MultipartFile> multipartFileList = createMultipartFiles();
    Long itemId = itemService.saveItem(itemFormDto, multipartFileList);

    // 2. 상품 정보 수정
    ItemFormDto updateFormDto = new ItemFormDto();
    updateFormDto.setItemNm("수정된 테스트상품");
    updateFormDto.setItemSellStatus(ItemSellStatus.SOLD_OUT);
    updateFormDto.setItemDetail("수정된 테스트 상품 입니다.");
    updateFormDto.setPrice(2000);
    updateFormDto.setStockNumber(50);

    itemService.updateItem(updateFormDto, multipartFileList);  // Assuming you have a method like this

    // 3. 수정된 정보 확인
    Item updatedItem = itemRepository.findById(itemId)
            .orElseThrow(EntityNotFoundException::new);

    assertEquals(updateFormDto.getItemNm(), updatedItem.getItemNm());
    assertEquals(updateFormDto.getItemSellStatus(), updatedItem.getItemSellStatus());
    assertEquals(updateFormDto.getItemDetail(), updatedItem.getItemDetail());
    assertEquals(updateFormDto.getPrice(), updatedItem.getPrice());
    assertEquals(updateFormDto.getStockNumber(), updatedItem.getStockNumber());
    // 이미지 관련 검증도 추가할 수 있습니다.
}
}