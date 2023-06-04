package com.example.practicalwork.DTO;

import com.example.practicalwork.models.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Component
@Schema (description = "Information about document")
public class DocumentDTO {
//    @Parameter(hidden = true)
    private Long id;

    @Schema (description = "Number of document")
    @NotBlank
    @Size(min = 1, max = 50)
    private String number;

    @Schema (description = "Type of document")
    @NotBlank
//    @Enumerated(EnumType.STRING)
    @JsonProperty("type")
    private String docTitle;

    @Parameter(hidden = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("created")
    private LocalDateTime createdAt;

    @Parameter(hidden = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("updated")
    private LocalDateTime updatedAt;

    @Schema (description = "List of related documents")
    @JsonProperty("related")
    private List<DocRelatedDTO> docRelatedList;

// Unnecessary field in document json
//    @Schema (description = "Initial template of the document")
//    @NotNull
//    private DocTemplate template;

    @Schema (description = "File of the document")
    private DocFile file;

    @Schema (description = "Fields of the document")
    private List<DocFieldDTO> fields;

//    @Schema (description = "Contractors of the document")
//    @NotNull
//    private List<ContractorDTO> contractors;

}
