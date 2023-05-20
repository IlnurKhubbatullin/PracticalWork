package com.example.practicalwork.controllers;

import com.example.practicalwork.DTO.DocTemplateDTO;
import com.example.practicalwork.convertors.DocTemplateConvertor;
import com.example.practicalwork.services.DocTemplateService;
import com.example.practicalwork.utils.DocTemplateListIsEmptyException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/template")
@AllArgsConstructor
public class DocTemplateController {
    private final DocTemplateService docTemplateService;
    private final DocTemplateConvertor docTemplateConvertor;

    @GetMapping("/all")
    public List<DocTemplateDTO> getAllTemplates() {
        List<DocTemplateDTO> list = docTemplateService.findAll()
                .stream().map(docTemplateConvertor::convertToDto)
                .toList();
        if (list.isEmpty()) {
            throw new DocTemplateListIsEmptyException();
        }
        return list;
    }

}
