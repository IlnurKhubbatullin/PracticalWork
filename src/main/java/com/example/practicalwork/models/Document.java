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
    @OneToMany (cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn (name = "document_id")
    private List<DocRelated> docRelatedList;

//    @OneToOne (fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL,
//            mappedBy = "document")
//    private DocTemplate template;
//    @OneToOne (fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL,
//            mappedBy = "document")
//    private DocFile file;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "file_id")
    private DocFile file;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "template_id")
    private DocTemplate template;

    @OneToMany (cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn (name = "document_id")
    private List<DocField> fields;
    @ManyToMany (mappedBy = "documents", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<Contractor> contractors;

}
