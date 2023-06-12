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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "template")
public class DocTemplate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private boolean removed;
    private String title;
    private String version;
    private DocTitle docTitle;

//    @OneToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "document_id", nullable = false)
//    private Document document;

    @OneToOne (fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "template")
    private Document document;

    @OneToMany (cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn (name = "template_id")
    private List<DocField> fields;

}
