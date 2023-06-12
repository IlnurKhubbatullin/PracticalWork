package com.example.practicalwork.controllers;

import com.example.practicalwork.DTO.DocumentDTO;
import com.example.practicalwork.converters.*;
import com.example.practicalwork.models.DocTemplate;
import com.example.practicalwork.models.DocTitle;
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

import java.util.List;

@RestController
@RequestMapping("/api/document")
@AllArgsConstructor
@Tag(name = "DocumentController", description = "API for documents")
public class DocumentController {
    private final DocumentService docService;
    private final DocTemplateService templateService;
    private final DocumentConverter docConverter;
    private final BindingResultHandler bindingResultHandler;

    @GetMapping("/all")
    @Operation(summary = "Get documents", description = "Get all current and removed documents")
    public List<DocumentDTO> getAll() {
        List<DocumentDTO> dto = docService.findAll()
                .stream().map(docConverter::convertToDto)
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
                .stream().map(docConverter::convertToDto)
                .toList();
        if (dto.isEmpty()) {
            throw new DocListIsEmptyException();
        }
        return dto;
    }

    @GetMapping("/filter")
    @Operation(summary = "Get documents", description = "Get documents filtered by type, number, date(YYYY-MM-DD) of creation, id of contractor and deletion status")
    public List<DocumentDTO> getFiltered(@RequestParam(value = "type", required = false) String enumType,
                                         @RequestParam(value = "number", required = false) String number,
                                         @RequestParam(value = "date", required = false) String date,
                                         @RequestParam(value = "contractor", required = false) String contractorId,
                                         @RequestParam(value = "current", required = false, defaultValue = "true")
                                         String current) {

        List<DocumentDTO> list = docService.findAll().stream()
                .filter(el -> {
                    if (Boolean.parseBoolean(current)) {
                        return !el.isRemoved();
                    } else return true;
                })

                .filter(el -> {
                    if (enumType != null) {
                        return el.getDocTitle().name().equalsIgnoreCase(enumType);
                    } else return true;
                })

                .filter(el -> {
                    if (contractorId != null) {
                        return el.getContractors().stream().anyMatch(c -> c.getId().equals(Long.parseLong(contractorId)));
                    } else return true;
                })

                .filter(el -> {
                    if (number != null) {
                        return el.getNumber().contains(number);
                    } else return true;
                })

                .filter(el -> {
                    if (date != null) {
                        return date.equals(el.getCreatedAt().toString().substring(0, 10));
                    } else return true;
                })

                .map(docConverter::convertToDto)
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
        return docConverter.convertToDto(docService.read(id));
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

    @PostMapping("/new/template/{id}")
    @Operation(summary = "Create document", description = "Create new document from template by id")
    public ResponseEntity<HttpStatus> createFromTemplate(@PathVariable("id") Long idTemplate) {

        // Exception set in DocTemplateService
        DocTemplate template = templateService.read(idTemplate);

        docService.create(docConverter.convertTemplateToDocument(template));
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

        docService.update(docConverter.convertToEntity(dto));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    void throwExceptionIfTypeIncorrect(DocumentDTO dto) {
        String type = DocTitle.findByValue(dto.getDocTitle());
        if (type == null) {
            throw new DocInvalidTypeOfDocException();
        } dto.setDocTitle(type.toUpperCase());
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
    public ResponseEntity<ErrorResponse> handlerException(DocIsDeletedException e) {
        e.printStackTrace();
        ErrorResponse response = new ErrorResponse();
        response.setMessage("Document is deleted");
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // Duplicated from DocTemplateController
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(DocTemplateNotFoundException e) {
        e.printStackTrace();
        ErrorResponse response = new ErrorResponse();
        response.setMessage("Template not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(DocInvalidTypeOfDocException e) {
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

//    @ExceptionHandler
//    public ResponseEntity<ErrorResponse> handlerException(DocInvalidFormatOfFileException e) {
//        e.printStackTrace();
//        ErrorResponse response = new ErrorResponse();
//        response.setMessage("Invalid format of file. Allowed: /docx, /xlsx, /pdf");
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }

    // @ExceptionHandler
    // ...

}
