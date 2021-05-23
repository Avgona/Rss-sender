package com.avgona.rss.sender.controllers;

import com.avgona.rss.sender.entities.Rss;
import com.avgona.rss.sender.services.EmailService;
import com.avgona.rss.sender.services.RssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.io.IOException;
import java.util.List;

@Controller
public class RssController {

    private final RssService rssService;
    private final EmailService emailService;
    private StringBuilder context = new StringBuilder("");

    @Autowired
    public RssController(RssService rssService, EmailService emailService) {
        this.rssService = rssService;
        this.emailService = emailService;
    }

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("rssObj", new Rss());
        model.addAttribute("context", context);

        return "index";
    }

    @PostMapping("/process")
    public void send(@ModelAttribute("rssObj") Rss rss, BindingResult bindingResult,
                     HttpServletResponse response, HttpServletRequest request) throws MessagingException, IOException {

        if(bindingResult.hasErrors())
            throw new ValidationException("EmailResponse is not valid");

        List<String> parsedFeed = rssService.parseFeed(rss.getRss());

        switch (request.getParameter("action")){
            case "save":
                rssService.save(rss);
                break;
            case "send":
                emailService.sendMail(rss.getEmail(), parsedFeed.get(0), parsedFeed.get(1));
                context = new StringBuilder("");
                break;
            case "preview":
                context = new StringBuilder(parsedFeed.get(1));
                break;
        }
        response.sendRedirect("/");
    }
}
