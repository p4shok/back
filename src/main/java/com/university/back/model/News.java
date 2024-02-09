package com.university.back.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "data")
    private String data;
}