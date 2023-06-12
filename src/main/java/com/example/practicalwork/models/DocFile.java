package com.example.practicalwork.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "file")
public class DocFile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private boolean removed;
    private String name;
    private Extension extension;
    private boolean isZip;
    private String store;
    private Long size;
    private String description;

//    @OneToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "document_id", nullable = false)
//    private Document document;

    @OneToOne (fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "file")
    private Document document;



}
