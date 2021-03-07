package com.avgona.rss.sender.controllers;


import com.avgona.rss.sender.entities.Rss;
import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.io.IOException;
import java.util.Collections;

@RestController
public class EmailRestController {

    private final EmailService emailService;

    @Autowired
    public EmailRestController(EmailService emailService) {
        this.emailService = emailService;
    }

    // Sends e-mail with rss by user's e-mail
    @PostMapping(value = "/process", params = "send")
    public void send(@ModelAttribute("action") Rss rss,
                       HttpServletResponse response,
                       BindingResult bindingResult) throws AddressException, IOException {

        if(bindingResult.hasErrors())
            throw new ValidationException("EmailResponse is not valid");


        Email email = DefaultEmail.builder()
                .from(new InternetAddress("wsb.team.2021@gmail.com"))
                .to(Collections.singleton(new InternetAddress(rss.getEmail())))
                .subject("You got a new RSS e-mail !")
                .body(rss.getRss())
                .encoding("UTF-8")
                .build();

        emailService.send(email);

        response.sendRedirect("/");
    }
}
