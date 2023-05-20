package com.example.practicalwork.convertors;

import com.example.practicalwork.DTO.DocFieldDTO;
import com.example.practicalwork.models.DocField;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DocFieldConvertor {
    private ModelMapper modelMapper;

    public DocFieldDTO convertToDto(DocField entity){

        return modelMapper.map(entity, DocFieldDTO.class);
    }
}
