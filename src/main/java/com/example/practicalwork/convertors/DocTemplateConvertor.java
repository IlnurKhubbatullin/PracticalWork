package com.example.practicalwork.convertors;

import com.example.practicalwork.DTO.DocTemplateDTO;
import com.example.practicalwork.models.DocTemplate;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DocTemplateConvertor {
    private ModelMapper modelMapper;

    public DocTemplateDTO convertToDto(DocTemplate entity){

        return modelMapper.map(entity, DocTemplateDTO.class);
    }
}
