package com.EChowk.EChowk.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConnectionEmailService {
    private final EmailService emailService;

    @Async
    public void sendConnectionEmail(String to,String subject,String message){
        emailService.sendEmail(to,subject,message);
    }
}
