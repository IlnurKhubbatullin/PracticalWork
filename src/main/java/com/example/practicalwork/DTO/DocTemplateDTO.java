package com.example.practicalwork.DTO;

import com.example.practicalwork.models.DocField;
import com.example.practicalwork.models.DocTitle;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Getter
@Component
@Schema (description = "Information about document's template")
public class DocTemplateDTO {
    private Long id;
    private String title;
    private String version;
    private DocTitle docTitle;
    @JsonProperty("fields")
    private List<DocField> templateFields;

}
