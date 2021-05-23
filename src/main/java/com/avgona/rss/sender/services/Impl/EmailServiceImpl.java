package com.avgona.rss.sender.services.Impl;

import com.avgona.rss.sender.config.JavaMailConfig;
import com.avgona.rss.sender.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final JavaMailConfig javaMailConfig;

    @Autowired
    public EmailServiceImpl(@Qualifier("getJavaMailSender") JavaMailSender javaMailSender, JavaMailConfig javaMailConfig) {
        this.javaMailSender = javaMailSender;
        this.javaMailConfig = javaMailConfig;
    }

    @Override
    public void sendMail(String to, String subject, String context) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(javaMailConfig.getUsername());
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(context, true);

        javaMailSender.send(message);
    }
}