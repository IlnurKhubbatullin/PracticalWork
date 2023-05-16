package com.example.practicalwork.services;

import com.example.practicalwork.models.Contractor;
import com.example.practicalwork.models.Document;
import com.example.practicalwork.repositories.ContractorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ContractorService {

    private final ContractorRepository contractorRepository;

    @Autowired
    public ContractorService(ContractorRepository contractorRepository) {
        this.contractorRepository = contractorRepository;
    }

    public List <Contractor> allContractors () {
        return contractorRepository.findAllContractors();
    }

    public Contractor findContractor (Long id) {
        Contractor contractor = contractorRepository.findContractorById(id).orElse(null);
        if (contractor != null && contractor.isRemoved() == false) {
            return contractor;
        }
        return null;
    }

    public Contractor updateContractor (Long id, Contractor updateContractor) {
        if (updateContractor != null) {
            Contractor contractor = findContractor(id);
            if (contractor == null) {
                return null;
            }
            contractor.setFirstName(updateContractor.getFirstName());
            contractor.setPatronymic(updateContractor.getPatronymic());
            contractor.setLastName(updateContractor.getLastName());
            contractor.setCountry(updateContractor.getCountry());
            contractor.setPhone(updateContractor.getPhone());
            contractor.setEmail(updateContractor.getEmail());
            contractor.setTelegram(updateContractor.getTelegram());
            contractorRepository.save(contractor);
            return contractor;
        } else {
            return null;
        }
    }

    public Contractor findContractorsByDocument(Document document) {
        Contractor contractor = contractorRepository.findContractorByDocuments(document)
    }

}
// уточнить про регистрацию