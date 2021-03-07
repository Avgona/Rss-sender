package com.avgona.rss.sender.repositories;

import com.avgona.rss.sender.entities.Rss;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RssRepository extends JpaRepository<Rss, Long> {

}
