package com.avgona.rss.sender.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Feed {

    private String title;
    private String description;
    private String link;

    private List<Item> itemHolder = new ArrayList<>();

    public Feed(String title, String description, String link) {
        this.title = title;
        this.description = description;
        this.link = link;
    }
}
