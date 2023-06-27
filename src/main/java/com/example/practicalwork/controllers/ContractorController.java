package com.example.practicalwork.controllers;

import com.example.practicalwork.DTO.ContractorDTO;
import com.example.practicalwork.models.Contractor;
import com.example.practicalwork.services.ContractorService;
import com.example.practicalwork.utils.BindingResultHandler;
import com.example.practicalwork.utils.ErrorResponse;
import com.example.practicalwork.utils.contractor.ContrIsDeletedException;
import com.example.practicalwork.utils.contractor.ContrListIsEmptyException;
import com.example.practicalwork.utils.contractor.ContrNotCreatedException;
import com.example.practicalwork.utils.contractor.ContrNotDeletedException;
import com.example.practicalwork.utils.document.DocInvalidTypeOfDocException;
import com.example.practicalwork.utils.document.DocIsDeletedException;
import com.example.practicalwork.utils.document.DocNotCreatedException;
import com.example.practicalwork.utils.document.DocNotDeletedException;
import com.example.practicalwork.utils.template.DocTemplateNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contractor")
public class ContractorController {

    private final ContractorService contractorService;
    private final ContractorDTO contractorDTO;
    private final ModelMapper modelMapper;
    private final BindingResultHandler bindingResultHandler;


    public ContractorController(ContractorService contractorService, ContractorDTO contractorDTO, ModelMapper modelMapper, BindingResultHandler bindingResultHandler) {
        this.contractorService = contractorService;
        this.contractorDTO = contractorDTO;
        this.modelMapper = modelMapper;
        this.bindingResultHandler = bindingResultHandler;
    }

    @GetMapping("/all")
    public List<ContractorDTO> getContractors () {
        return contractorService.allContractors().stream()
                .map(this::convertToContractor)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ContractorDTO getById (@PathVariable("id") Long id) {
        return convertToContractor(contractorService.findContractor(id));
    }

    @GetMapping("/current")
    public List<ContractorDTO> getCurrent() {
        List<ContractorDTO> contr = contractorService.findCurrent()
                .stream().map(this::convertToContractor)
                .toList();
        if ((contr.isEmpty())) {
            throw new ContrListIsEmptyException();
        }
        return contr;
    }

    @GetMapping("/byDoc")
    public List<ContractorDTO> getContractorByDocument (@RequestParam(value = "document", required = false) String documentId){
        List<ContractorDTO> list = contractorService.allContractors().stream()
                .filter(x -> {
                    if (documentId != null) {
                        return x.getDocuments().stream().anyMatch(c -> c.getId().equals(Long.parseLong(documentId)));
                    } else return true;
                })
                .map(this::convertToContractor)
                .toList();
        return list;
    }

    @GetMapping("/filter")
    public List<ContractorDTO> getContractorByFilter (@RequestParam(value = "firstName", required = false) String firstName,
                                                      @RequestParam(value = "patronymic", required = false) String patronymic,
                                                      @RequestParam(value = "lastName", required = false) String lastName,
                                                      @RequestParam(value = "country", required = false) String country,
                                                      @RequestParam(value = "credential", required = false) String credentialId,
                                                      @RequestParam(value = "current", required = false, defaultValue = "true")
                                                      String current) {
        List<ContractorDTO> list = contractorService.allContractors().stream()
                .filter(x -> {
                    if (Boolean.parseBoolean(current)) {
                        return !x.isRemoved();
                    } else return true;
                })
                .filter(x -> {
                    if (firstName != null) {
                        return x.getFirstName().contains(firstName);
                    } else return true;
                })
                .filter(x -> {
                    if (patronymic != null) {
                        return x.getPatronymic().contains(patronymic);
                    } else return true;
                })
                .filter(x -> {
                    if (lastName != null) {
                        return x.getLastName().contains(lastName);
                    } else return true;
                })
                .filter(x -> {
                    if (country != null) {
                        return x.getCountry().contains(country);
                    } else return true;
                })
                .filter(x -> {
                    if (credentialId != null) {
                        return x.getCredential().getId().equals(Long.parseLong(credentialId));
                    } else return true;
                })
                .map(this::convertToContractor)
                .toList();
        if (list.isEmpty()) {
            throw new ContrListIsEmptyException();
        }
        return list;
    }

    private ContractorDTO convertToContractor (Contractor contractor) {
        return modelMapper.map(contractor, ContractorDTO.class);
    }
    public Contractor convertToContractorDTO (ContractorDTO contractorDTO) {
        return modelMapper.map(contractorDTO, Contractor.class);
    }

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> createdContractor (@RequestBody @Valid ContractorDTO creatContractor,
                                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String sad = bindingResultHandler.createErrorMessage(bindingResult);
            throw new ContrNotCreatedException(sad);
        }
        contractorService.saveCreatedContractor(convertToContractorDTO(creatContractor));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<HttpStatus> updateContractor (@RequestBody @Valid ContractorDTO updateContractor,
                                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String das = bindingResultHandler.createErrorMessage(bindingResult);
            throw new ContrNotCreatedException(das);
        }
        contractorService.updateContractor(convertToContractorDTO(updateContractor));
        return ResponseEntity.ok(HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteContractor (@PathVariable("id") Long id) {
        contractorService.deleteContractor(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(ContrIsDeletedException e) {
        e.printStackTrace();
        ErrorResponse response = new ErrorResponse();
        response.setMessage("Contractor is deleted");
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(ContrNotCreatedException e) {
        e.printStackTrace();
        ErrorResponse response = new ErrorResponse();
        response.setMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
