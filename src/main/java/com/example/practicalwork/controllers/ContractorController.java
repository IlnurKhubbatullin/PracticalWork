package com.example.practicalwork.controllers;

import com.example.practicalwork.DTO.ContractorDTO;
import com.example.practicalwork.models.Contractor;
import com.example.practicalwork.models.Credential;
import com.example.practicalwork.models.Document;
import com.example.practicalwork.services.ContractorService;
//import io.swagger.models.Response;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/contractor")
public class ContractorController {

    private final ContractorService contractorService;
    private final ContractorDTO contractorDTO;
    private final ModelMapper modelMapper;


    public ContractorController(ContractorService contractorService, ContractorDTO contractorDTO, ModelMapper modelMapper) {
        this.contractorService = contractorService;
        this.contractorDTO = contractorDTO;
        this.modelMapper = modelMapper;
    }

//    @GetMapping()
//    public List<ContractorDTO> getContractors () {
//        return contractorService.allContractors().stream()
//                .map(this::convertToContractor)
//                .collect(Collectors.toList());
//    }

//    @GetMapping
//    public Contractor getContractorByDocument (Contractor contractor, @RequestParam List<Document> document) {
//        return contractorService.findContractorByDocuments(document);
//    }
//
//    //УТОЧНИТЬ НИЖЕ
//    @GetMapping
//    public Contractor getContractorByFilter (Contractor contractor, @RequestParam String firstName, @RequestParam String patronymic,
//                                             @RequestParam String lastName, @RequestParam String country, @RequestParam Credential credential) {
//        return contractorService.findContractorByFilter(contractor);
//    }

    private ContractorDTO convertToContractor (Contractor contractor) {
        return modelMapper.map(contractor, ContractorDTO.class);
    }

    @PostMapping
    public ResponseEntity<ContractorDTO> createdContractor (@RequestBody @Valid Contractor contractor) {
       Contractor newContractor = contractorService.saveCreatedContractor(contractor);
        return ResponseEntity.accepted().body(convertToContractor(newContractor));
    }

//    @PostMapping("/{id}")
//    public ResponseEntity<ContractorDTO> updateContractor (@RequestBody @Valid Contractor updateContractor, @PathVariable Long id) {
//        Contractor newContractor = contractorService.updateContractor(id, updateContractor);
//        return ResponseEntity.accepted().body(convertToContractor(newContractor));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Response> deleteContractor (@PathVariable("id") Long id) {
//        contractorService.deleteContractor(id);
//        return ResponseEntity.accepted().body(new Response());
//    }

}
