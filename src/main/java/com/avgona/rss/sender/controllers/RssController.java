package com.avgona.rss.sender.controllers;

import com.avgona.rss.sender.entities.Feed;
import com.avgona.rss.sender.entities.Rss;
import com.avgona.rss.sender.parsers.RssFeedParser;
import com.avgona.rss.sender.services.Impl.EmailService;
import com.avgona.rss.sender.services.RssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.io.IOException;

@Controller
public class RssController {

    private final RssService rssService;
    private final RssFeedParser rssFeedParser;
    private final EmailService emailService;


    private StringBuilder context = new StringBuilder("");

    @Autowired
    public RssController(RssService rssService, RssFeedParser rssFeedParser, EmailService emailService) {
        this.rssService = rssService;
        this.rssFeedParser = rssFeedParser;
        this.emailService = emailService;
    }

    @GetMapping("/")
    public String index(Model model){

        Rss rss = new Rss();

        model.addAttribute("action", rss);
        model.addAttribute("context", context);

        return "index";
    }

    // Saves data into database
    @PostMapping(value = "/process", params = "save")
    public String save(@ModelAttribute("action") Rss rss){

        rssService.save(rss);

        return "redirect:/";
    }

    // Sends e-mail with rss by user's e-mail
    @PostMapping(value = "/process", params = "send")
    public void send(@ModelAttribute("action") Rss rss,
                     HttpServletResponse response,
                     BindingResult bindingResult) throws MessagingException, IOException {

        if(bindingResult.hasErrors())
            throw new ValidationException("EmailResponse is not valid");

        context = new StringBuilder("");

        rssFeedParser.setUrl(rss.getRss());
        Feed feed = rssFeedParser.readFeed();

        for(int i = 0; i < feed.getItemHolder().size(); i++){
            context.append(rssService.composeText(feed.getItemHolder().get(i).getTitle(),
                    feed.getItemHolder().get(i).getDescription(),
                    feed.getItemHolder().get(i).getLink()));
        }

        emailService.sendMail(rss.getEmail(), feed.getTitle(), String.valueOf(context));
        context = new StringBuilder("");

        response.sendRedirect("/");
    }

    // Sends e-mail with rss by user's e-mail
    @PostMapping(value = "/process", params = "preview")
    public void send(@ModelAttribute("action") Rss rss,
                     HttpServletResponse response,
                     BindingResult bindingResult,
                     Model model) throws IOException {

        if(bindingResult.hasErrors())
            throw new ValidationException("EmailResponse is not valid");

        rssFeedParser.setUrl(rss.getRss());
        Feed feed = rssFeedParser.readFeed();

        for(int i = 0; i < feed.getItemHolder().size(); i++){
            context.append(rssService.composeText(feed.getItemHolder().get(i).getTitle(),
                    feed.getItemHolder().get(i).getDescription(),
                    feed.getItemHolder().get(i).getLink()));
        }


        response.sendRedirect("/");
    }
}
