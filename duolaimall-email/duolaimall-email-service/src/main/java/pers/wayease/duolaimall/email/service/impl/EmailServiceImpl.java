package pers.wayease.duolaimall.email.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pers.wayease.duolaimall.common.constant.ResultCodeEnum;
import pers.wayease.duolaimall.common.exception.ThirdPartyException;
import pers.wayease.duolaimall.email.pojo.param.OrderEmailParam;
import pers.wayease.duolaimall.email.service.EmailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 0.1
 * @project duolaimall
 * @package pers.wayease.duolaimall.email.service.impl
 * @name EmailServiceImpl
 * @description Email service implement class.
 * @since 2024-10-05 20:46
 */
@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSenderImpl javaMailSenderImpl;
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${info.app.email}")
    private String testEmail;

    @Override
    public void testConnection() {
        log.info("+ === Test SMTP connection");
        log.info("| username: {}", username);
        log.info("| password: {}", password);
        try {
            javaMailSenderImpl.testConnection();
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new ThirdPartyException(ResultCodeEnum.SMTP_CONNECTION_ERROR);
        }
    }

    private void sendTextMailMessage(String subject, String content, String... to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);
        message.setText(content);
        message.setSentDate(new Date());
        message.setFrom(username);
        message.setTo(to);
        javaMailSenderImpl.send(message);
    }

    private void sendHtmlMailMessage(String subject, String content, String... to) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(content, true);
        mimeMessageHelper.setSentDate(new Date());
        mimeMessageHelper.setFrom(username);
        mimeMessageHelper.setTo(to);
        javaMailSender.send(mimeMessage);
    }

    @Override
    public void test(String to) throws MessagingException {
        sendHtmlMailMessage(
                "duolaimall 测试邮件",
                "<!DOCTYPE html>\n" +
                        "<html lang=\"zh-CN\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>测试邮件</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "    <h1>测试邮件</h1>\n" +
                        "    <p>这是一封测试邮件，用于检查邮件发送功能是否正常。</p>\n" +
                        "    \n" +
                        "    <!-- 按钮，点击后会打开邮件客户端回复邮件 -->\n" +
                        "    <a href=\"mailto:yu_weiyi@outlook.com\" style=\"display:inline-block; padding:10px 20px; background-color:#4CAF50; color:white; text-decoration:none; border-radius:5px;\">回复作者</a>\n" +
                        "</body>\n" +
                        "</html>",
                to == null ? testEmail : to
        );
    }

    @Override
    public void sendOrderEmail(OrderEmailParam orderEmailParam) throws MessagingException {
        StringBuilder emailStringBuilder = new StringBuilder();
        emailStringBuilder
                .append("<!DOCTYPE html><html lang=\"zh\"><head><meta charset=\"UTF-8\">")
                .append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">")
                .append("<title>查看您的订单</title><style>")
                .append("body { font-family: Arial, sans-serif; color: #333; }")
                .append(".content { padding: 20px; text-align: center; }")
                .append(".email { font-size: 16px; margin-bottom: 20px; }")
                .append(".link { font-size: 18px; font-weight: bold; margin-top: 20px; }")
                .append("a { color: #007bff; text-decoration: none; }")
                .append("</style></head><body>")
                .append("<div class=\"content\"><p class=\"email\">您的邮箱：")
                .append(orderEmailParam.getEmail())
                .append("</p><p class=\"link\"><a href=\"")
                .append(orderEmailParam.getUrl())
                .append("\" target=\"_blank\">点击这里查看订单详情</a></p></div></body></html>");
        String htmlString = emailStringBuilder.toString();

        sendHtmlMailMessage("查看您的订单 -- duolaimall", htmlString, orderEmailParam.getEmail());
    }
}
