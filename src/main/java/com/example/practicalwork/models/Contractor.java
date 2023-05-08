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
    @Column (name = "id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "first_name")
    private String firstName;

    @Column (name = "patronymic")
    private String patronymic;

    @Column (name = "last_name")
    private String lastName;

//    @Column (name = "credential_id")
//    private Long credentialId;
    @OneToOne
    private Credential credential;

    @Column (name = "country")
    private String country;

    @Column (name = "phone")
    private String phone;

    @Column (name = "email")
    private String email;

    @Column (name = "telegram")
    private String telegram;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List <Comment> comments;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    private List <Document> document;

    @Column (name = "removed")
    private boolean removed;


}
