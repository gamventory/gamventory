package com.gamventory.config;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailManager {

    private String sender;

    private JavaMailSender javaMailSender;

    public void send(String sendTo, String sub, String con) throws Exception {

        // HTML 태그 그대로 전송하는 방법
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage.setFrom(sender);

        // 수신자

    }
}
