package com.example.practicalwork.controllers;

import com.example.practicalwork.DTO.DocumentDTO;
import com.example.practicalwork.converters.*;
import com.example.practicalwork.models.DocTemplate;
import com.example.practicalwork.models.DocTitle;
import com.example.practicalwork.models.Document;
import com.example.practicalwork.models.Extension;
import com.example.practicalwork.services.DocTemplateService;
import com.example.practicalwork.services.DocumentService;
import com.example.practicalwork.utils.*;
import com.example.practicalwork.utils.document.*;
import com.example.practicalwork.utils.template.DocTemplateNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/document")
@AllArgsConstructor
@Tag(name = "DocumentController", description = "API for documents")
public class DocumentController {
    private final DocumentService docService;
    private final DocTemplateService templateService;
    private final DocumentConverter docConvertor;
    private final BindingResultHandler bindingResultHandler;
    private final ToDocxConverter toDocxConverter;
    private final ToXlsxConverter toXlsxConverter;
    private final ToPdfConverter toPdfConverter;
    private final ToZipConverter toZipConverter;

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
            throw new DocNotCreatedException(str);
        }

        docService.update(docConvertor.convertToEntity(dto));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    void throwExceptionIfTypeIncorrect(DocumentDTO dto) {
        String type = DocTitle.findByValue(dto.getDocTitle());
        if (type == null) {
            throw new DocUnknownTypeOfDocException();
        } dto.setDocTitle(type.toUpperCase());
    }

    @GetMapping("/{id}/{format}")
    @Operation(summary = "Create file", description = "Create file using document id. Formats: docx, xlsx, pdf. To create an archive use ?zip=true, default value false")
    public ResponseEntity<FileUriResponse> createFileFromDoc(@PathVariable("id") Long id,
                                                        @PathVariable("format") String f,
                                                        @RequestParam(value = "zip", required = false,
                                                                defaultValue = "false") boolean isZip)
                                                        throws IOException {
        Document doc = docService.read(id);

        Extension format = Extension.findByValue(f);
        if (format == null) {
            throw new DocInvalidFormatOfFileException();
        }

        // To do create DocFile

        switch (format) {
            case DOCX -> toDocxConverter.convert(doc);
            case XLSX -> toXlsxConverter.convert(doc);
            case PDF -> toPdfConverter.convert(doc);
        }

        if (isZip) {
            toZipConverter.convert(doc);
        }

        FileUriResponse fileUriResponse = new FileUriResponse();

        return new ResponseEntity<>(fileUriResponse, HttpStatus.OK);

    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(DocListIsEmptyException e) {
        e.printStackTrace();
        ErrorResponse response = new ErrorResponse();
        response.setMessage("No documents for this request");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(DocNotFoundException e) {
        e.printStackTrace();
        ErrorResponse response = new ErrorResponse();
        response.setMessage("Document not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(DocNotDeletedException e) {
        e.printStackTrace();
        ErrorResponse response = new ErrorResponse();
        response.setMessage("Document doesn't need in recovery");
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(DocTemplateNotFoundException e) {
        e.printStackTrace();
        ErrorResponse response = new ErrorResponse();
        response.setMessage("Template not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(DocUnknownTypeOfDocException e) {
        e.printStackTrace();
        ErrorResponse response = new ErrorResponse();
        response.setMessage("Incorrect type of document");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(DocNotCreatedException e) {
        e.printStackTrace();
        ErrorResponse response = new ErrorResponse();
        response.setMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(DocInvalidFormatOfFileException e) {
        e.printStackTrace();
        ErrorResponse response = new ErrorResponse();
        response.setMessage("Invalid format of file. Allowed: /docx, /xlsx, /pdf");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // @ExceptionHandler
    // ...

}
