package com.avgona.rss.sender.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "email_rss")
public class Rss {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "rss")
    private String rss;

}
