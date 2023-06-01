package com.example.practicalwork.convertors;

import com.example.practicalwork.DTO.DocFieldDTO;
import com.example.practicalwork.DTO.DocumentDTO;
import com.example.practicalwork.models.DocTemplate;
import com.example.practicalwork.models.Document;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DocumentConvertor {
    private ModelMapper modelMapper;
    private DocFieldConvertor docFieldConvertor;

    public DocumentDTO convertToDto(Document entity) {
//        DocumentDTO dto = modelMapper.map(entity, DocumentDTO.class);
//        dto.setFields(entity.getFields().stream()
//                .map(el -> modelMapper.map(el, DocFieldDTO.class)).toList());
//        return dto;
        return modelMapper.map(entity, DocumentDTO.class);
    }

    public Document convertToEntity(DocumentDTO dto) {

        return modelMapper.map(dto, Document.class);
    }

    public Document convertTemplateToDocument(DocTemplate template) {
        Document document = new Document();
        document.setDocTitle(template.getDocTitle());
        document.setTemplate(template);
        document.setFields(template.getFields());
        return document;
    }

}
