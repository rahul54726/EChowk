package com.EChowk.EChowk.Service;

import com.EChowk.EChowk.exception.ResourceNotFoundException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendWelcomeEmail(String toEmail, String userName) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("vermarahul11034@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("Welcome To SkillHub");
            helper.setText(
                    "<h3>Hello " + userName + ",</h3>" +
                            "<p>Welcome to <b>EChowk</b> – your peer-to-peer skill exchange hub!</p>" +
                            "<p>We're glad to have you onboard. Start sharing and discovering skills today.</p>" +
                            "<br><p>Cheers,<br>SkillHub</p>", true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new ResourceNotFoundException("Failed to send Email");
        }
    }

    public void sendEmail(String toEmail, String subject, String body) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("vermarahul11034@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true); // Supports HTML
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new ResourceNotFoundException("Failed to send Email");
        }
    }
    public void sendPasswordResetEmail(String toEmail, String resetLink) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("vermarahul11034@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("Reset Your Password - EChowk");
            helper.setText("<p>Click the link below to reset your password:</p>" +
                    "<a href=\"" + resetLink + "\">Reset Password</a>", true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send Password Reset Email", e);
        }
    }

}
