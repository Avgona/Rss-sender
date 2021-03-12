package com.avgona.rss.sender.controllers;


import com.avgona.rss.sender.entities.Feed;
import com.avgona.rss.sender.entities.Rss;
import com.avgona.rss.sender.parsers.RssFeedParser;
import com.avgona.rss.sender.services.Impl.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.io.IOException;

@RestController
public class EmailRestController {

    private final EmailService emailService;
    private final RssFeedParser rssFeedParser;

    @Autowired
    public EmailRestController(EmailService emailService, RssFeedParser rssFeedParser) {
        this.emailService = emailService;
        this.rssFeedParser = rssFeedParser;
    }

    // Sends e-mail with rss by user's e-mail
    @PostMapping(value = "/process", params = "send")
    public void send(@ModelAttribute("action") Rss rss,
                       HttpServletResponse response,
                       BindingResult bindingResult) throws MessagingException, IOException {

        if(bindingResult.hasErrors())
            throw new ValidationException("EmailResponse is not valid");

        rssFeedParser.setUrl(rss.getRss());

        Feed feed = rssFeedParser.readFeed();

        for(int i = 0; i < feed.getItemHolder().size(); i++){
            emailService.composeText(feed.getItemHolder().get(i).getTitle(),
                                    feed.getItemHolder().get(i).getDescription(),
                                    feed.getItemHolder().get(i).getLink());
        }

        emailService.sendMail(rss.getEmail(), feed.getTitle());

        response.sendRedirect("/");
    }
}
