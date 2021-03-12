package com.avgona.rss.sender.services.Impl;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
public class EmailService{

    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String username;

    private StringBuilder text = new StringBuilder("");

    @Autowired
    public EmailService(@Qualifier("getJavaMailSender") JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(String to, String subject) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(username);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(String.valueOf(text), true);

        javaMailSender.send(message);
        text = new StringBuilder("");
    }

    public void composeText(String header, String context, String link){
        text.append(String.format(
                "<html><head><style>img {width: %s;}</style></head><body>" +
                        "<h3 style='text-align:center'>%s</h3>" +
                        "\t<p'>%s</p>" +
                        "<br />Link : %s" +
                        "</body></html>", "100%", header, context, link));
    }
}