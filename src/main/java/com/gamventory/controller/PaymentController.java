package com.gamventory.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamventory.service.PaymentService;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    //db의 값과 js에서 넘어온 값이 일치하는지 체크하는 코드
    @PostMapping("/verify-payment")
    public ResponseEntity<Map<String, Boolean>> verifyPayment(@RequestBody Map<String, String> requestData) {
        String price = requestData.get("price");
        String itemNm = requestData.get("itemNm");

        boolean isValid = paymentService.verifyPaymentData(price, itemNm);
        System.out.println("일치여부 테스트");
        System.out.println("가격 : "+ price);
        System.out.println("상품명 : "+ itemNm);
        System.out.println("벨류에 들어간 값 : "+ isValid);

        Map<String, Boolean> response = new HashMap<>();
        response.put("isValid", isValid);
        return ResponseEntity.ok(response);
    }
}
