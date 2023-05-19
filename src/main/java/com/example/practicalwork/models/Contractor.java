package com.example.practicalwork.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table (name = "contractor")
public class Contractor {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String patronymic;

    private String lastName;

    @OneToOne
    private Credential credential;

    private String country;

    private String phone;

    private String email;

    private String telegram;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List <Comment> comments;

    @ManyToMany
    private List <Document> documents;

    private boolean removed;


}
