package com.example.practicalwork.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class DocFieldDTO {
    private Long id;
    private String name;
    private String type;
    private String placeholder;
    private String defaultValue;

}
