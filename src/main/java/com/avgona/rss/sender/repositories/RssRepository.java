package com.avgona.rss.sender.repositories;

import com.avgona.rss.sender.entities.Rss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RssRepository extends JpaRepository<Rss, Long> {

}
