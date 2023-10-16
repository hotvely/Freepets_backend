package com.kh.Freepets.domain.member;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


@Component
public class EmailService
{
    @Autowired
    private JavaMailSender sender;


    public String sendEmail(String toAddress, String tempPwd) throws MessagingException
    {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);


        // helper 객체에 보내려고 하는 메세지의 구성 요소를 담아서
        helper.setTo(toAddress);
        helper.setSubject("임시 발급 비밀번호");
        helper.setText("임시 비밀번호는 : [" + tempPwd + "] 입니다. 홈페이지 로그인 이후 비밀번호 변경 해 주세요.");
        sender.send(message);

        return "Success";


    }

}
