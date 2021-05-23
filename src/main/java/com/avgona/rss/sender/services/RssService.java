package com.avgona.rss.sender.services;

import com.avgona.rss.sender.entities.Rss;

import java.util.List;

public interface RssService {

    void save(Rss rss);

    List<String> parseFeed(String urlRss);

    String composeText(String header, String context, String link);
}
