package com.example.practicalwork.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Document implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private DocTitle docTitle;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private Boolean removed;

    @OneToMany
    @JoinColumn (name = "document_id")
    private List<DocRelated> docRelatedList;

    @OneToOne
    private DocTemplate template;

    @OneToOne
    private DocFile file;

    @OneToMany
    @JoinColumn (name = "document_id")
    private List<DocField> completedFields;

    @ManyToMany (mappedBy = "documents")
    private Set<Contractor> contractors;

}
