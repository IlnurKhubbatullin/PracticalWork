package com.example.practicalwork.DTO;

import com.example.practicalwork.models.DocField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Getter
@Component
public class DocTemplateDTO {
    private Long id;
    private String title;
    private String version;
    @JsonProperty("fields")
    private List<DocField> templateFields;

}
