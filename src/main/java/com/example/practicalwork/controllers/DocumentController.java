package com.example.practicalwork.controllers;

import com.example.practicalwork.DTO.DocTemplateDTO;
import com.example.practicalwork.DTO.DocumentDTO;
import com.example.practicalwork.convertors.DocumentConvertor;
import com.example.practicalwork.models.DocTemplate;
import com.example.practicalwork.models.DocTitle;
import com.example.practicalwork.services.DocTemplateService;
import com.example.practicalwork.services.DocumentService;
import com.example.practicalwork.utils.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/document")
@AllArgsConstructor
@Tag(name = "DocumentController", description = "API for documents")
public class DocumentController {
    private final DocumentService docService;
    private final DocTemplateService templateService;
    private final DocumentConvertor docConvertor;
    private final BindingResultHandler bindingResultHandler;

    @GetMapping("/all")
    @Operation(summary = "Get documents", description = "Get all current and removed documents")
    public List<DocumentDTO> getAll() {
        List<DocumentDTO> dto = docService.findAll()
                .stream().map(docConvertor::convertToDto)
                .toList();
        if (dto.isEmpty()) {
            throw new DocListIsEmptyException();
        }
        return dto;
    }

    @GetMapping("/current")
    @Operation(summary = "Get documents", description = "Get current documents only (removed = false)")
    public List<DocumentDTO> getCurrent() {
        List<DocumentDTO> dto = docService.findCurrent()
                .stream().map(docConvertor::convertToDto)
                .toList();
        if (dto.isEmpty()) {
            throw new DocListIsEmptyException();
        }
        return dto;
    }

    @GetMapping()
    @Operation(summary = "Get documents", description = "Get documents filtered by type, number, date of creation, id of contractor and deletion status")
    public List<DocumentDTO> getFiltered(@RequestParam(value = "type", required = false) String enumType,
                                         @RequestParam(value = "number", required = false) String number,
                                         @RequestParam(value = "date", required = false) String date,
                                         @RequestParam(value = "contractor", required = false) String contractorId,
                                         @RequestParam(value = "current", required = false, defaultValue = "true")
                                         String current) {

        List<DocumentDTO> list = docService.findAll().stream()
                .filter(el -> !el.isRemoved() && Boolean.parseBoolean(current))
                .filter(el -> el.getDocTitle().name().equals(enumType) && !enumType.isEmpty())
                .filter(el -> el.getContractors()
                        .stream().anyMatch(c -> c.getId().equals(Long.parseLong(contractorId))) && !contractorId.isEmpty())
                .filter(el -> el.getNumber().contains(number) && !number.isEmpty())
                .filter(el -> date.equals(el.getCreatedAt().toString()) && !date.isEmpty())
                .map(docConvertor::convertToDto)
                .toList();

        if (list.isEmpty()) {
            throw new DocListIsEmptyException();
        }
        return list;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get document", description = "Get one document by id")
    public DocumentDTO getById(@PathVariable("id") Long id) {

        // Exception set in DocumentService
        return docConvertor.convertToDto(docService.read(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete document", description = "Delete one document by id")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {

        // Exception set in DocumentService
        docService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/recovery/{id}")
    @Operation(summary = "Recovery document", description = "Recovery one document by id")
    public ResponseEntity<HttpStatus> recovery(@PathVariable("id") Long id) {

        // Exception set in DocumentService
        docService.recovery(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/new/from-template/{id}")
    @Operation(summary = "Create document", description = "Create new document from template by id")
    public ResponseEntity<HttpStatus> createFromTemplate(@PathVariable("id") Long idTemplate) {

        // Exception set in DocTemplateService
        DocTemplate template = templateService.read(idTemplate);

        docService.create(docConvertor.convertTemplateToDocument(template));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/update")
    @Operation(summary = "Update document", description = "Update current document by id in json")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid DocumentDTO dto,
                                             BindingResult bindingResult) {
        throwExceptionIfTypeIncorrect(dto);

        if (bindingResult.hasErrors()) {
            String str = bindingResultHandler.createErrorMessage(bindingResult);
            throw new DocumentNotCreatedException(str);
        }

        docService.update(docConvertor.convertToEntity(dto));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    void throwExceptionIfTypeIncorrect(DocumentDTO dto) {
        String type = DocTitle.findByValue(dto.getDocTitle());
        if (type == null) {
            throw new DocumentUnknownTypeOfDocument();
        } dto.setDocTitle(type.toUpperCase());
    }

    @PostMapping("/file-to-document/{id}")
    @Operation(summary = "Create file", description = "Create file using document id")
    public ResponseEntity<HttpStatus> createFileFromDocument(@PathVariable("id") Long idTemplate) {

        // To do document to file converter
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<DocumentErrorResponse> handlerException(DocListIsEmptyException e) {
        e.printStackTrace();
        DocumentErrorResponse response = new DocumentErrorResponse();
        response.setMessage("No documents for this request");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<DocumentErrorResponse> handlerException(DocNotFoundException e) {
        e.printStackTrace();
        DocumentErrorResponse response = new DocumentErrorResponse();
        response.setMessage("Document not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<DocumentErrorResponse> handlerException(DocumentNotDeletedException e) {
        e.printStackTrace();
        DocumentErrorResponse response = new DocumentErrorResponse();
        response.setMessage("Document doesn't need in recovery");
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<DocTemplateErrorResponse> handlerException(DocTemplateNotFoundException e) {
        e.printStackTrace();
        DocTemplateErrorResponse response = new DocTemplateErrorResponse();
        response.setMessage("Template not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<DocumentErrorResponse> handlerException(DocumentUnknownTypeOfDocument e) {
        e.printStackTrace();
        DocumentErrorResponse response = new DocumentErrorResponse();
        response.setMessage("Incorrect type of document");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<DocumentErrorResponse> handlerException(DocumentNotCreatedException e) {
        e.printStackTrace();
        DocumentErrorResponse response = new DocumentErrorResponse();
        response.setMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // @ExceptionHandler
    // ...

}
