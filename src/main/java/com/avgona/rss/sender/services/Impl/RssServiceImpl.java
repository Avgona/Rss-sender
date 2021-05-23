package com.avgona.rss.sender.services.Impl;

import com.avgona.rss.sender.entities.Feed;
import com.avgona.rss.sender.entities.Rss;
import com.avgona.rss.sender.parsers.RssFeedParser;
import com.avgona.rss.sender.repositories.RssRepository;
import com.avgona.rss.sender.services.RssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RssServiceImpl implements RssService {

    private final RssFeedParser rssFeedParser;
    private final RssRepository rssRepository;

    @Autowired
    public RssServiceImpl(RssFeedParser rssFeedParser, RssRepository rssRepository) {
        this.rssFeedParser = rssFeedParser;
        this.rssRepository = rssRepository;
    }

    @Override
    public void save(Rss rss) {
        rssRepository.save(rss);
    }

    @Override
    public List<String> parseFeed(String url){
        StringBuilder context = new StringBuilder("");

        rssFeedParser.setUrl(url);
        Feed feed = rssFeedParser.readFeed();

        for(int i = 0; i < feed.getItemHolder().size(); i++){
            context.append(composeText(feed.getItemHolder().get(i).getTitle(),
                    feed.getItemHolder().get(i).getDescription(),
                    feed.getItemHolder().get(i).getLink()));
        }
        return Arrays.asList(feed.getTitle(), String.valueOf(context));
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
