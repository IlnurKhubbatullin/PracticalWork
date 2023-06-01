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
@Table (name = "document")
public class Document implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private boolean removed;
    private String number;
    private DocTitle docTitle;
    @OneToMany (cascade = CascadeType.ALL)
    @JoinColumn (name = "document_id")
    private List<DocRelated> docRelatedList;
    @OneToOne (cascade = CascadeType.ALL)
    private DocTemplate template;
    @OneToOne (cascade = CascadeType.ALL)
    private DocFile file;
    @OneToMany (cascade = CascadeType.ALL)
    @JoinColumn (name = "document_id")
    private List<DocField> fields;
    @ManyToMany (mappedBy = "documents", cascade = CascadeType.ALL)
    private Set<Contractor> contractors;

}
