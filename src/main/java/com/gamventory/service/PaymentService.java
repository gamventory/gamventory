package com.gamventory.service;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamventory.entity.Item;
import com.gamventory.repository.ItemRepository;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class PaymentService {

    private final OkHttpClient client = new OkHttpClient();

    //post방식으로 요청을 보내는 메서드
    @Autowired
    private ItemRepository itemRepository; // 상품 정보를 조회하기 위한 리포지토리

    public String preparePayment() {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create("{\n" +
                                            "    \"merchant_uid\": \"...\", \n" + // 가맹점 주문번호
                                            "    \"amount\": 420000 \n" + // 결제 예정금액
                                            "}", mediaType);

        Request request = new Request.Builder()
                .url("https://api.iamport.kr/payments/prepare")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error occurred: " + e.getMessage();
        }
    }

    //db의 상품의 값과 입력된 매개변수의 상품 가격이 일치하는지 체크하는 로직
    public boolean verifyPaymentData(String price, String itemNm) {
        int parsedPrice = Integer.parseInt(price);
        Item item = itemRepository.findByItemNmAndPrice(itemNm,parsedPrice); // DB에서 상품 정보 조회

        //test출력값
        System.out.println("아이템 서비스의 값: " + item.getPrice());
        System.out.println("아이템 서비스 이름: "+ item.getItemNm());
        System.out.println("비교대상의 값" + price);

        if (item != null && item.getPrice() == parsedPrice) {
            return true;
        }
        return false;
    }
}
