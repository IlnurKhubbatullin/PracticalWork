package com.example.practicalwork.controllers;

import com.example.practicalwork.DTO.DocFileDTO;
import com.example.practicalwork.converters.DocFileConverter;
import com.example.practicalwork.services.DocFileService;
import com.example.practicalwork.utils.BindingResultHandler;
import com.example.practicalwork.utils.file.DocFileListIsEmptyException;
import com.example.practicalwork.utils.file.DocFileNotCreatedException;
import com.example.practicalwork.utils.file.DocFileNotDeletedException;
import com.example.practicalwork.utils.file.DocFileNotFoundException;
import com.example.practicalwork.utils.ErrorResponse;
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
@RequestMapping("/api/file")
@AllArgsConstructor
@Tag(name = "DocFileController", description = "API for files")
public class DocFileController {

    private final DocFileService docFileService;
    private final DocFileConverter docFileConverter;
    private final BindingResultHandler bindingResultHandler;

    @GetMapping("/all")
    @Operation(summary = "Get files", description = "Get all current and removed files")
    public List<DocFileDTO> getAll() {
        List<DocFileDTO> dto = docFileService.findAll()
                .stream().map(docFileConverter::convertToDto)
                .toList();
        if (dto.isEmpty()) {
            throw new DocFileListIsEmptyException();
        }
        return dto;
    }

    @GetMapping("/current")
    @Operation(summary = "Get files", description = "Get current files only (removed = false)")
    public List<DocFileDTO> getCurrent() {
        List<DocFileDTO> dto = docFileService.findCurrent()
                .stream().map(docFileConverter::convertToDto)
                .toList();
        if (dto.isEmpty()) {
            throw new DocFileListIsEmptyException();
        }
        return dto;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get file", description = "Get one file by id")
    public DocFileDTO getById(@PathVariable("id") Long id) {

        // Exception set in DocFileService
        return docFileConverter.convertToDto(docFileService.read(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete file", description = "Delete one file by id")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {

        // Exception set in DocFieldService
        docFileService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/recovery/{id}")
    @Operation(summary = "Recovery file", description = "Recovery one file by id")
    public ResponseEntity<HttpStatus> recovery(@PathVariable("id") Long id) {

        // Exception set in DocFileService
        docFileService.recovery(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/new")
    @Operation(summary = "Create file", description = "Create new file using data in json")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid DocFileDTO dto,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String str = bindingResultHandler.createErrorMessage(bindingResult);
            throw new DocFileNotCreatedException(str);
        }
        docFileService.create(docFileConverter.convertToEntity(dto));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/update")
    @Operation(summary = "Update file", description = "Update current file by id in json")
    public ResponseEntity<HttpStatus> update(@RequestBody DocFileDTO dto,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String str = bindingResultHandler.createErrorMessage(bindingResult);
            throw new DocFileNotCreatedException(str);
        }
        docFileService.update(docFileConverter.convertToEntity(dto));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(DocFileListIsEmptyException e) {
        e.printStackTrace();
        ErrorResponse response = new ErrorResponse();
        response.setMessage("No files for this request");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(DocFileNotFoundException e) {
        e.printStackTrace();
        ErrorResponse response = new ErrorResponse();
        response.setMessage("File not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(DocFileNotDeletedException e) {
        e.printStackTrace();
        ErrorResponse response = new ErrorResponse();
        response.setMessage("File doesn't need in recovery");
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(DocFileNotCreatedException e) {
        e.printStackTrace();
        ErrorResponse response = new ErrorResponse();
        response.setMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
