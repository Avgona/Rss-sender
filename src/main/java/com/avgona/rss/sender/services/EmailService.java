package com.avgona.rss.sender.services;

import javax.mail.MessagingException;

public interface EmailService {

    void sendMail(String to, String subject, String context) throws MessagingException;
}
