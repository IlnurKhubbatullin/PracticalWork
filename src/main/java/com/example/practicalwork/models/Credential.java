package com.example.practicalwork.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table (name = "credential")
public class Credential {

    @Id
    @Column(name = "id")
    private Long id;

    @Column (name = "text")
    private String text;

    @Column(name = "version")
    private String version;

    @Column (name = "created_at")
    private LocalDateTime createdAt;

    @OneToOne
    private TypeOfContractor typeOfContractor;

    @Column (name = "removed")
    private boolean removed;




}
