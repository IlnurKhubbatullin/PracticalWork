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
@Table(name = "comment")
public class Comment {

    @Id
    @Column (name = "id")
    private Long id;

    @Column (name = "text")
    private String text;

    @Column (name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn (name = "contactor_id", referencedColumnName = "id")
    private Contractor comment;

    @Column (name = "removed")
    private boolean removed;

}
