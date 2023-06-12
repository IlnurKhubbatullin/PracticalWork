package com.example.practicalwork.DTO;

import com.example.practicalwork.models.Document;
import com.example.practicalwork.models.Extension;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@Schema(description = "Information about file of document")
public class DocFileDTO {
    @Schema (description = "Id of the file")
    private Long id;
    @Schema (description = "Name of the file")
    private String name;
    @Schema (description = "Format of the file")
    private Extension extension;
    @Schema (description = "Zip archive")
    @JsonProperty("zip")
    private boolean isZip;
    @Schema (description = "Store of the file")
    private String store;
    @Schema (description = "Size of the file in Bytes")
    private Long size;
    @Schema (description = "Description of the file")
    @NotBlank
    private String description;

//    @Schema (description = "Id of document")
//    private Document document;

}
