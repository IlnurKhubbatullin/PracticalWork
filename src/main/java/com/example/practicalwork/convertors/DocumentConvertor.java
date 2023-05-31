package com.example.practicalwork.convertors;

import com.example.practicalwork.DTO.DocumentDTO;
import com.example.practicalwork.models.Document;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DocumentConvertor {

    private ModelMapper modelMapper;

    public DocumentDTO convertToDto(Document entity){
        return modelMapper.map(entity, DocumentDTO.class);
    }
}
