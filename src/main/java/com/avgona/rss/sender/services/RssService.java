package com.avgona.rss.sender.services;

import com.avgona.rss.sender.entities.Rss;

public interface RssService {

    void save(Rss rss);

    String composeText(String header, String context, String link);
}
