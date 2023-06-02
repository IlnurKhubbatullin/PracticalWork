package com.example.practicalwork.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@Schema(description = "Information about field of document and template")
public class DocFieldDTO {
//    @Parameter(hidden = true)
    private Long id;
    @Schema (description = "Name of field")
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;
    @Schema (description = "Type of field")
    @NotBlank
    @Size(min = 1, max = 50)
    private String type;
    @Schema (description = "Placeholder of field")
    private String placeholder;
    @Schema (description = "Default value of field")
    @JsonProperty("default")
    private String defaultValue;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("type", type)
                .append("placeholder", placeholder)
                .append("default", defaultValue)
                .toString();

    }
}
