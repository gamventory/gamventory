package com.gamventory.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.ui.ModelMap;

import com.gamventory.constant.Category;
import com.gamventory.constant.Platform;
import com.gamventory.dto.ItemFilterSearchDto;
import com.gamventory.entity.Item;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ListControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

   @Test
    public void testSearchItems() throws Exception {
        // MockMvc를 사용하여 컨트롤러를 테스트
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // 검색 조건을 설정
        ItemFilterSearchDto searchDto = new ItemFilterSearchDto();
        Category category = Category.ACTION; // 예시: 카테고리 설정
        Platform platform = Platform.STEAM; // 예시: 플랫폼 설정

        String categoryString = category.toString();
        String platformString = platform.toString();

        // 페이지 설정
        Pageable pageable = PageRequest.of(0, 10);

        // 검색 요청 보내기
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/list/list")
                        .param("category", categoryString)
                        .param("platform", platformString)
                        .param("page", String.valueOf(pageable.getPageNumber()))
                        .param("size", String.valueOf(pageable.getPageSize()))
        ).andReturn();

        // 검색 결과 확인
        ModelMap modelMap = result.getModelAndView().getModelMap(); // ModelMap을 사용합니다.
        Page<Item> searchResults = (Page<Item>) modelMap.get("searchResults");

        // 검색 결과 검증
        assertEquals(200, result.getResponse().getStatus()); // HTTP 상태 코드 확인
        assertEquals(searchDto.getCategory(), searchResults.getContent().get(0).getCategory());
        assertEquals(searchDto.getPlatform(), searchResults.getContent().get(0).getPlatform());
    }
}

