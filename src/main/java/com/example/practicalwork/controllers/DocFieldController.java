package com.example.practicalwork.controllers;

import com.example.practicalwork.DTO.DocFieldDTO;
import com.example.practicalwork.convertors.DocFieldConvertor;
import com.example.practicalwork.services.DocFieldService;
import com.example.practicalwork.utils.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/field")
@AllArgsConstructor
@Tag(name = "DocFieldController", description = "API for fields")
public class DocFieldController {
    private final DocFieldService docFieldService;
    private final DocFieldConvertor docFieldConvertor;

    @GetMapping("/all")
    @Operation(summary = "Get fields", description = "Get all current and removed fields")
    public List<DocFieldDTO> getAll() {
        List<DocFieldDTO> list = docFieldService.findAll()
                .stream().map(docFieldConvertor::convertToDto)
                .toList();
        if (list.isEmpty()) {
            throw new DocFieldListIsEmptyException();
        }
        return list;
    }

    @GetMapping("/current")
    @Operation(summary = "Get fields", description = "Get current fields only (removed = false)")
    public List<DocFieldDTO> getCurrent() {
        List<DocFieldDTO> list = docFieldService.findCurrent()
                .stream().map(docFieldConvertor::convertToDto)
                .toList();
        if (list.isEmpty()) {
            throw new DocFieldListIsEmptyException();
        }
        return list;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get field", description = "Get one field by id")
    public DocFieldDTO getById(@PathVariable("id") Long id) {

        // Add exception
        return docFieldConvertor.convertToDto(docFieldService.read(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete field", description = "Delete one field by id")
//    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {

        // Add exception
        docFieldService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/new")
    @Operation(summary = "Create field", description = "Create new field using data in json")
//    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HttpStatus> create(@RequestBody DocFieldDTO dto) {

        // Add exception
        docFieldService.create(docFieldConvertor.convertToEntity(dto));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/update")
    @Operation(summary = "Update field", description = "Update current field by id in json")
//    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HttpStatus> update(@RequestBody DocFieldDTO dto) {

        // Add exception
        docFieldService.update(docFieldConvertor.convertToEntity(dto));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<DocFieldErrorResponse> handelException(DocFieldListIsEmptyException e) {
        e.printStackTrace();
        DocFieldErrorResponse response = new DocFieldErrorResponse();
        response.setMessage("There are no fields");
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler
    public ResponseEntity<DocFieldErrorResponse> handelException(DocFieldNotFoundException e) {
        e.printStackTrace();
        DocFieldErrorResponse response = new DocFieldErrorResponse();
        response.setMessage("Field not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // @ExceptionHandler
    // ...



}
