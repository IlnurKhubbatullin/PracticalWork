package com.example.practicalwork.controllers;

import com.example.practicalwork.DTO.DocTemplateDTO;
import com.example.practicalwork.convertors.DocTemplateConvertor;
import com.example.practicalwork.models.DocTemplate;
import com.example.practicalwork.models.DocTitle;
import com.example.practicalwork.services.DocTemplateService;
import com.example.practicalwork.utils.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/template")
@AllArgsConstructor
@Tag(name = "DocTemplateController", description = "API for templates")
public class DocTemplateController {
    private final DocTemplateService docTemplateService;
    private final DocTemplateConvertor docTemplateConvertor;
    private final BindingResultHandler bindingResultHandler;

    @GetMapping("/all")
    @Operation(summary = "Get templates", description = "Get all current and removed templates")
    public List<DocTemplateDTO> getAll() {

        List<DocTemplateDTO> dto = docTemplateService.findAll()
                .stream().map(docTemplateConvertor::convertToDto)
                .toList();
        if (dto.isEmpty()) {
            throw new DocTemplateListIsEmptyException();
        }
        return dto;
    }

    @GetMapping("/current")
    @Operation(summary = "Get templates", description = "Get current templates only (removed = false)")
    public List<DocTemplateDTO> getCurrent() {
        List<DocTemplateDTO> dto = docTemplateService.findCurrent()
                .stream().map(docTemplateConvertor::convertToDto)
                .toList();
        if (dto.isEmpty()) {
            throw new DocTemplateListIsEmptyException();
        }
        return dto;
    }

    @GetMapping("type/{type}")
    @Operation(summary = "Get templates", description = "Get one type current templates (contract, agreement, application, act, reference)")
    public List<DocTemplateDTO> getByType(@PathVariable("type") String type) {

        if (DocTitle.findByValue(type) == null) {
            throw new DocTemplateUnknownTypeOfDocument();
        }

        List<DocTemplate> list = docTemplateService.findCurrent()
                .stream().filter(el -> el.getDocTitle().name().equalsIgnoreCase(type))
                .toList();

        if (list.isEmpty()) {
            throw new DocTemplateListIsEmptyException();
        }

        return list.stream().map(docTemplateConvertor::convertToDto).toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get template", description = "Get one template by id")
    public DocTemplateDTO getById(@PathVariable("id") Long id) {

        // Exception set in DocTemplateService
        return docTemplateConvertor.convertToDto(docTemplateService.read(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete template", description = "Delete one template by id")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {

        // Exception set in DocTemplateService
        docTemplateService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/recovery/{id}")
    @Operation(summary = "Recovery template", description = "Recovery one template by id")
    public ResponseEntity<HttpStatus> recovery(@PathVariable("id") Long id) {

        // Exception set in DocTemplateService
        docTemplateService.recovery(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/new")
    @Operation(summary = "Create template", description = "Create new template using data in json")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid DocTemplateDTO dto,
                                             BindingResult bindingResult) {

        throwExceptionIfTypeIncorrect(dto);

        if (bindingResult.hasErrors()) {
            String str = bindingResultHandler.createErrorMessage(bindingResult);
            throw new DocTemplateNotCreatedException(str);
        }

        docTemplateService.create(docTemplateConvertor.convertToEntity(dto));
        return ResponseEntity.ok(HttpStatus.OK);

    }

    @PutMapping("/update")
    @Operation(summary = "Update template", description = "Update current template by id in json")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid DocTemplateDTO dto,
                                             BindingResult bindingResult) {

        throwExceptionIfTypeIncorrect(dto);

        if (bindingResult.hasErrors()) {
            String str = bindingResultHandler.createErrorMessage(bindingResult);
            throw new DocTemplateNotCreatedException(str);
        }

        docTemplateService.update(docTemplateConvertor.convertToEntity(dto));
        return ResponseEntity.ok(HttpStatus.OK);

    }

    void throwExceptionIfTypeIncorrect(DocTemplateDTO dto) {
        String type = DocTitle.findByValue(dto.getDocTitle());
        if (type == null) {
            throw new DocTemplateUnknownTypeOfDocument();
        } dto.setDocTitle(type.toUpperCase());
    }

    @ExceptionHandler
    public ResponseEntity<DocTemplateErrorResponse> handlerException(DocTemplateListIsEmptyException e) {
        e.printStackTrace();
        DocTemplateErrorResponse response = new DocTemplateErrorResponse();
        response.setMessage("No templates for this request");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<DocTemplateErrorResponse> handlerException(DocTemplateNotFoundException e) {
        e.printStackTrace();
        DocTemplateErrorResponse response = new DocTemplateErrorResponse();
        response.setMessage("Template not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<DocTemplateErrorResponse> handlerException(DocTemplateNotDeletedException e) {
        e.printStackTrace();
        DocTemplateErrorResponse response = new DocTemplateErrorResponse();
        response.setMessage("Template doesn't need in recovery");
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<DocTemplateErrorResponse> handlerException(DocTemplateNotCreatedException e) {
        e.printStackTrace();
        DocTemplateErrorResponse response = new DocTemplateErrorResponse();
        response.setMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<DocTemplateErrorResponse> handlerException(DocTemplateUnknownTypeOfDocument e) {
        e.printStackTrace();
        DocTemplateErrorResponse response = new DocTemplateErrorResponse();
        response.setMessage("Incorrect type of template");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // @ExceptionHandler
    // ...

}
