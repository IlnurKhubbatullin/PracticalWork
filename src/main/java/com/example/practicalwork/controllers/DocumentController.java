package com.example.practicalwork.controllers;

import com.example.practicalwork.DTO.DocumentDTO;
import com.example.practicalwork.convertors.DocumentConvertor;
import com.example.practicalwork.models.DocTemplate;
import com.example.practicalwork.services.DocTemplateService;
import com.example.practicalwork.services.DocumentService;
import com.example.practicalwork.utils.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/document")
@AllArgsConstructor
@Tag(name = "DocumentController", description = "API for documents")
public class DocumentController {
    private final DocumentService docService;
    private final DocTemplateService templateService;
    private final DocumentConvertor docConvertor;

    @GetMapping("/all")
    @Operation(summary = "Get documents", description = "Get all current and removed documents")
    public List<DocumentDTO> getAll() {
        List<DocumentDTO> list = docService.findAll()
                .stream().map(docConvertor::convertToDto)
                .toList();
        if (list.isEmpty()) {
            throw new DocListIsEmptyException();
        }
        return list;
    }

    @GetMapping("/current")
    @Operation(summary = "Get documents", description = "Get current documents only (removed = false)")
    public List<DocumentDTO> getCurrent() {
        List<DocumentDTO> list = docService.findCurrent()
                .stream().map(docConvertor::convertToDto)
                .toList();
        if (list.isEmpty()) {
            throw new DocListIsEmptyException();
        }
        return list;
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

        // Add exception
        return docConvertor.convertToDto(docService.read(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete document", description = "Delete one document by id")
//    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {

        // Add exception
        docService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/new/from-template/{id}")
    @Operation(summary = "Create document", description = "Create new document from template by id")
//    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HttpStatus> createFromTemplate(@PathVariable("id") Long idTemplate) {
        DocTemplate template = templateService.read(idTemplate);

        // Add exception
        docService.create(docConvertor.convertTemplateToDocument(template));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/update")
    @Operation(summary = "Update document", description = "Update current document by id in json")
//    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HttpStatus> update(@RequestBody DocumentDTO dto) {

        // Add exception
        docService.update(docConvertor.convertToEntity(dto));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<DocumentErrorResponse> handelException(DocListIsEmptyException e) {
        e.printStackTrace();
        DocumentErrorResponse response = new DocumentErrorResponse();
        response.setMessage("There are no documents");
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler
    public ResponseEntity<DocumentErrorResponse> handelException(DocNotFoundException e) {
        e.printStackTrace();
        DocumentErrorResponse response = new DocumentErrorResponse();
        response.setMessage("Document not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // @ExceptionHandler
    // ...

}
