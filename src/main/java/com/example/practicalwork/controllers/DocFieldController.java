package com.example.practicalwork.controllers;

import com.example.practicalwork.DTO.DocFieldDTO;
import com.example.practicalwork.convertors.DocFieldConvertor;
import com.example.practicalwork.services.DocFieldService;
import com.example.practicalwork.utils.DocFieldListIsEmptyException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/field")
@AllArgsConstructor
public class DocFieldController {

    private final DocFieldService docFieldService;
    private final DocFieldConvertor docFieldConvertor;

    @GetMapping("/all")
    public List<DocFieldDTO> getAllFields() {
        List<DocFieldDTO> list = docFieldService.findAll()
                .stream().map(docFieldConvertor::convertToDto)
                .toList();
        if (list.isEmpty()) {
            throw new DocFieldListIsEmptyException();
        }
        return list;
    }

}
