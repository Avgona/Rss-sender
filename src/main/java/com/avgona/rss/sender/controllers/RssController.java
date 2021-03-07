package com.avgona.rss.sender.controllers;

import com.avgona.rss.sender.entities.Rss;
import com.avgona.rss.sender.services.RssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RssController {

    private final RssService rssService;

    @Autowired
    public RssController(RssService rssService) {
        this.rssService = rssService;
    }

    @GetMapping("/")
    public String index(Model model){

        Rss rss = new Rss();

        model.addAttribute("action", rss);

        return "index";
    }

    // Saves data into database
    @PostMapping(value = "/process", params = "save")
    public String save(@ModelAttribute("action") Rss rss){

        rssService.save(rss);

        return "redirect:/";
    }


}
