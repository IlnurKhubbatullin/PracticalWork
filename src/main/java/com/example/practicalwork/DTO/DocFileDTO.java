package com.example.practicalwork.DTO;

import com.example.practicalwork.models.Document;
import com.example.practicalwork.models.Mimetype;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@Schema(description = "Information about file of document")
public class DocFileDTO {
    @Schema (description = "Id of file")
    private Long id;
    @Schema (description = "Name of file")
    @NotBlank
    private String name;
    @Schema (description = "Format of file")
    @NotBlank
    private Mimetype mimetype;
    @Schema (description = "Store of file")
    private String store;
    @Schema (description = "Size of file in Bytes")
    private Long size;
    @Schema (description = "Description of file")
    @NotBlank
    private String description;
    @Schema (description = "Id of document")
    private Document document;

}
