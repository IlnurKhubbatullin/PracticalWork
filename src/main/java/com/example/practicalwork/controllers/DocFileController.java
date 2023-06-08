package com.example.practicalwork.controllers;

import com.example.practicalwork.DTO.DocFileDTO;
import com.example.practicalwork.converters.DocFileConverter;
import com.example.practicalwork.services.DocFileService;
import com.example.practicalwork.utils.file.DocFileListIsEmptyException;
import com.example.practicalwork.utils.file.DocFileNotDeletedException;
import com.example.practicalwork.utils.file.DocFileNotFoundException;
import com.example.practicalwork.utils.ErrorResponse;
import com.example.practicalwork.utils.template.DocTemplateNotDeletedException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/file")
@AllArgsConstructor
@Tag(name = "DocFileController", description = "API for files")
public class DocFileController {

    private final DocFileService docFileService;
    private final DocFileConverter docFileConverter;

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

}
