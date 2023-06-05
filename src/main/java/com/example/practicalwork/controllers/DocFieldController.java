package com.example.practicalwork.controllers;

import com.example.practicalwork.DTO.DocFieldDTO;
import com.example.practicalwork.convertors.DocFieldConvertor;
import com.example.practicalwork.services.DocFieldService;
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
@RequestMapping("/api/field")
@AllArgsConstructor
@Tag(name = "DocFieldController", description = "API for fields")
public class DocFieldController {
    private final DocFieldService docFieldService;
    private final DocFieldConvertor docFieldConvertor;
    private final BindingResultHandler bindingResultHandler;

    @GetMapping("/all")
    @Operation(summary = "Get fields", description = "Get all current and removed fields")
    public List<DocFieldDTO> getAll() {
        List<DocFieldDTO> dto = docFieldService.findAll()
                .stream().map(docFieldConvertor::convertToDto)
                .toList();
        if (dto.isEmpty()) {
            throw new DocFieldListIsEmptyException();
        }
        return dto;
    }

    @GetMapping("/current")
    @Operation(summary = "Get fields", description = "Get current fields only (removed = false)")
    public List<DocFieldDTO> getCurrent() {
        List<DocFieldDTO> dto = docFieldService.findCurrent()
                .stream().map(docFieldConvertor::convertToDto)
                .toList();
        if (dto.isEmpty()) {
            throw new DocFieldListIsEmptyException();
        }
        return dto;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get field", description = "Get one field by id")
    public DocFieldDTO getById(@PathVariable("id") Long id) {

        // Exception set in DocFieldService
        return docFieldConvertor.convertToDto(docFieldService.read(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete field", description = "Delete one field by id")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {

        // Exception set in DocFieldService
        docFieldService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/recovery/{id}")
    @Operation(summary = "Recovery field", description = "Recovery one field by id")
    public ResponseEntity<HttpStatus> recovery(@PathVariable("id") Long id) {

        // Exception set in DocFieldService
        docFieldService.recovery(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/new")
    @Operation(summary = "Create field", description = "Create new field using data in json")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid DocFieldDTO dto,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String str = bindingResultHandler.createErrorMessage(bindingResult);
            throw new DocFieldNotCreatedException(str);
        }
        docFieldService.create(docFieldConvertor.convertToEntity(dto));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/update")
    @Operation(summary = "Update field", description = "Update current field by id in json")
    public ResponseEntity<HttpStatus> update(@RequestBody DocFieldDTO dto,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String str = bindingResultHandler.createErrorMessage(bindingResult);
            throw new DocFieldNotCreatedException(str);
        }
        docFieldService.update(docFieldConvertor.convertToEntity(dto));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<DocFieldErrorResponse> handlerException(DocFieldListIsEmptyException e) {
        e.printStackTrace();
        DocFieldErrorResponse response = new DocFieldErrorResponse();
        response.setMessage("No fields for this request");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<DocFieldErrorResponse> handlerException(DocFieldNotFoundException e) {
        e.printStackTrace();
        DocFieldErrorResponse response = new DocFieldErrorResponse();
        response.setMessage("Field not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<DocFieldErrorResponse> handlerException(DocFieldNotDeletedException e) {
        e.printStackTrace();
        DocFieldErrorResponse response = new DocFieldErrorResponse();
        response.setMessage("Field doesn't need in recovery");
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<DocFieldErrorResponse> handlerException(DocFieldNotCreatedException e) {
        e.printStackTrace();
        DocFieldErrorResponse response = new DocFieldErrorResponse();
        response.setMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // @ExceptionHandler
    // ...



}
