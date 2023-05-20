package com.example.practicalwork.convertors;

import com.example.practicalwork.DTO.DocumentDTO;
import com.example.practicalwork.models.Document;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DocumentConvertor {
    private ModelMapper modelMapper;

    public DocumentDTO convertToDto(Document entity) {

        return modelMapper.map(entity, DocumentDTO.class);
    }

    public Document convertToEntity(DocumentDTO dto) {

        return modelMapper.map(dto, Document.class);
    }
}
