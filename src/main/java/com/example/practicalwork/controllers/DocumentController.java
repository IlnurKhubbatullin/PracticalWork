package com.example.practicalwork.controllers;

import com.example.practicalwork.DTO.DocumentDTO;
import com.example.practicalwork.convertors.DocumentConvertor;
import com.example.practicalwork.services.DocumentService;
import com.example.practicalwork.utils.DocListIsEmptyException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/document")
@AllArgsConstructor
public class DocumentController {
    private final DocumentService docService;
    private final DocumentConvertor docConvertor;

    @GetMapping("/all")
    public List<DocumentDTO> getAllDocs() {
        List<DocumentDTO> list = docService.findAll()
                .stream().map(docConvertor::convertToDto)
                .toList();
        if (list.isEmpty()) {
            throw new DocListIsEmptyException();
        }
        return list;
    }


}
