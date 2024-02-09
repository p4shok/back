package com.university.back.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "files")
public class MemberFile {
    @Column(name = "login")
    private String login;
    @Id
    @Column(name = "title")
    private String title;
    @Column(name = "type")
    private String type;
    @Lob
    @Column(name = "data", columnDefinition = "longblob")
    private String data;
}