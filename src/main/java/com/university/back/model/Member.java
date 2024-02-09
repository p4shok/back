package com.university.back.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "members")
public class Member {
    @Column(name = "first_name")
    private String name;
    @Column(name = "last_name")
    private String surname;
    @Id
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "education_group")
    private String group;
    @Column(name = "role")
    private String role;
    @Column(name = "visit_counter")
    private int visitCounter;
    @Column(name = "session_id")
    private String sessionId;
    @Lob
    @Column(name = "img", columnDefinition = "longblob")
    private byte[] img;

    public void increaseVisitCounter() {
        visitCounter++;
    }
    public void refactor() {
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        surname = surname.substring(0, 1).toUpperCase() + surname.substring(1);
        if (group != null)
            group = group.toUpperCase();
    }
    public void setDefaults() {
        visitCounter = 1;
        role = "student";
        img = null;
        refactor();
    }
}