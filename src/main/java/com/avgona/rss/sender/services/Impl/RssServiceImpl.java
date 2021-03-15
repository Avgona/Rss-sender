package com.avgona.rss.sender.services.Impl;

import com.avgona.rss.sender.entities.Rss;
import com.avgona.rss.sender.repositories.RssRepository;
import com.avgona.rss.sender.services.RssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RssServiceImpl implements RssService {

    private final RssRepository rssRepository;

    @Autowired
    public RssServiceImpl(RssRepository rssRepository) {
        this.rssRepository = rssRepository;
    }

    @Override
    public void save(Rss rss) {
        rssRepository.save(rss);
    }

    @Override
    public String composeText(String header, String context, String link){
        return String.format(
                "<html><head><style>img {width: %s;}</style></head><body>" +
                        "<h3 style='text-align:center'>%s</h3>" +
                        "\t<p'>%s</p>" +
                        "<br />Link : %s" +
                        "</body></html>", "100%", header, context, link);
    }
}
