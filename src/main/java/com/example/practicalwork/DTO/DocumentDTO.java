package com.example.practicalwork.DTO;

import com.example.practicalwork.models.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Component
@Schema (description = "Information about document")
public class DocumentDTO {
    private Long id;
    private String number;
    @Enumerated(EnumType.STRING)
    @JsonProperty("title")
    private DocTitle docTitle;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("created")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("updated")
    private LocalDateTime updatedAt;

    @JsonProperty("related")
    private List<DocRelated> docRelatedList;

    private DocTemplate template;

    private DocFile file;

    @JsonProperty("fields")
    private List<DocField> completedFields;

    private Set<ContractorDTO> contractors;

}
