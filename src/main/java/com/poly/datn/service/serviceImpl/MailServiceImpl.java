package com.poly.datn.service.serviceImpl;

import com.poly.datn.service.MailService;
import com.poly.datn.utils.MailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final MailUtil mailUtil;

    @Async
    @Override
    public void sendOrderStatus(String id, String status, String mail) {
        try {
            mailUtil.sendOrderStatus(id, status, mail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    @Async
    @Override
    public void sendEmailVerification(String code, String email) {
        try {
            mailUtil.sendEmailVerification(code,email);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


}
