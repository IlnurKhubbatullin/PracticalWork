package com.example.practicalwork.controllers;

import com.example.practicalwork.DTO.DocTemplateDTO;
import com.example.practicalwork.convertors.DocTemplateConvertor;
import com.example.practicalwork.services.DocTemplateService;
import com.example.practicalwork.utils.DocTemplateErrorResponse;
import com.example.practicalwork.utils.DocTemplateListIsEmptyException;
import com.example.practicalwork.utils.DocTemplateNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/template")
@AllArgsConstructor
@Tag (name = "DocTemplateController", description = "API for templates")
public class DocTemplateController {
    private final DocTemplateService docTemplateService;
    private final DocTemplateConvertor docTemplateConvertor;

    @GetMapping("/all")
    @Operation (summary = "Get all current and removed templates")
    public List<DocTemplateDTO> getAll() {
        List<DocTemplateDTO> list = docTemplateService.findAll()
                .stream().map(docTemplateConvertor::convertToDto)
                .toList();
        if (list.isEmpty()) {
            throw new DocTemplateListIsEmptyException();
        }
        return list;
    }

    @GetMapping("/current")
    @Operation (summary = "Get current templates only (removed = false)")
    public List<DocTemplateDTO> getCurrent() {
        List<DocTemplateDTO> list = docTemplateService.findCurrent()
                .stream().map(docTemplateConvertor::convertToDto)
                .toList();
        if (list.isEmpty()) {
            throw new DocTemplateListIsEmptyException();
        }
        return list;
    }

    @GetMapping("type/{type}")
    @Operation (summary = "Get one type current templates " +
            "(contract, agreement, application, act, reference")
    public List<DocTemplateDTO> getByType(@PathVariable("type") String type) {
        String enumType = type.toUpperCase();
        List<DocTemplateDTO> list = docTemplateService.findCurrent()
                .stream().filter(el -> el.getDocTitle().name().equals(enumType))
                .map(docTemplateConvertor::convertToDto)
                .toList();
        if (list.isEmpty()) {
            throw new DocTemplateListIsEmptyException();
        }
        return list;
    }

    @GetMapping("/{id}")
    @Operation (summary = "Get one template by id")
    public DocTemplateDTO getById(@PathVariable("id") Long id) {
        // Add exception
        return docTemplateConvertor.convertToDto(docTemplateService.read(id));
    }

    @DeleteMapping("/{id}")
    @Operation (summary = "Delete one template by id")
//    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {
        // Add exception
        docTemplateService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/new")
    @Operation (summary = "Create new template")
//    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HttpStatus> create(@RequestBody DocTemplateDTO dto) {
        // Add exception
        docTemplateService.create(docTemplateConvertor.convertToEntity(dto));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/update")
    @Operation (summary = "Update template")
//    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HttpStatus> update(@RequestBody DocTemplateDTO dto) {
        // Add exception
        docTemplateService.update(docTemplateConvertor.convertToEntity(dto));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<DocTemplateErrorResponse> handelException(DocTemplateListIsEmptyException e) {
        e.printStackTrace();
        DocTemplateErrorResponse response = new DocTemplateErrorResponse();
        response.setMessage("There are no templates");
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler
    public ResponseEntity<DocTemplateErrorResponse> handelException(DocTemplateNotFoundException e) {
        e.printStackTrace();
        DocTemplateErrorResponse response = new DocTemplateErrorResponse();
        response.setMessage("Template not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // @ExceptionHandler
    // ...

}
