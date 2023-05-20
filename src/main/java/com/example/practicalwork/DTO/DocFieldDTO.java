package com.example.practicalwork.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@Schema(description = "Information about fields of documents and templates")
public class DocFieldDTO {
    private Long id;
    private String name;
    private String type;
    private String placeholder;
    private String defaultValue;

}
