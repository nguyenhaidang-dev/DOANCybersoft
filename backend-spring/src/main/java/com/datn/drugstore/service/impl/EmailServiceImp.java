package com.datn.drugstore.service.impl;

import com.datn.drugstore.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImp implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.name}")
    private String appName;

    @Value("${app.url}")
    private String appUrl;

    @Override
    public void sendWelcomeEmail(String to, String fullName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail, appName);
            helper.setTo(to);
            helper.setSubject("🎉 Chào mừng bạn đến với " + appName);

            String content = """
                    <div style="font-family: Arial, sans-serif; line-height: 1.6;">
                        <h2>Xin chào %s 👋</h2>
                        <p>Cảm ơn bạn đã đăng ký tài khoản tại <b>%s</b>.</p>
                        <p>Tài khoản của bạn đã được tạo thành công.</p>

                        <p>
                            👉 <a href="%s" 
                               style="display:inline-block;padding:10px 16px;
                               background-color:#2e7d32;color:#fff;
                               text-decoration:none;border-radius:5px;">
                               Truy cập hệ thống
                            </a>
                        </p>

                        <p>Nếu bạn không thực hiện hành động này, vui lòng bỏ qua email.</p>

                        <hr/>
                        <p style="font-size:12px;color:#888;">
                            © %s - Hệ thống quản lý nhà thuốc
                        </p>
                    </div>
                    """.formatted(fullName, appName, appUrl, appName);

            helper.setText(content, true);
            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Không thể gửi email chào mừng", e);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi hệ thống khi gửi email", e);
        }
    }
}
