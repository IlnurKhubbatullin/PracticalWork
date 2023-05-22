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
        return contractorRepository.findContractorById(id);
    }

    public Contractor updateContractor (Long id, Contractor updateContractor) {
            Contractor contractor = findContractor(id);
            contractor.setFirstName(updateContractor.getFirstName());
            contractor.setPatronymic(updateContractor.getPatronymic());
            contractor.setLastName(updateContractor.getLastName());
            contractor.setCountry(updateContractor.getCountry());
            contractor.setPhone(updateContractor.getPhone());
            contractor.setEmail(updateContractor.getEmail());
            contractor.setTelegram(updateContractor.getTelegram());
            contractor.setCredential(updateContractor.getCredential());
            contractorRepository.save(contractor);
            return contractor;
    }

    public Contractor findContractorByDocuments (List<Document> document) {
            return contractorRepository.findContractorByDocuments(document);
    }

    //УТОЧНИТЬ НИЖЕ
    public Contractor findContractorByFilter (Contractor contractor) {
        return contractorRepository.findContractorByFilter(contractor.getFirstName(), contractor.getPatronymic(),
                contractor.getLastName(), contractor.getCountry(), contractor.getCredential());
    }

    public Contractor saveCreatedContractor (Contractor createdContractor) {
        Contractor contractor = new Contractor();
        if (createdContractor != null) {
        contractor.setFirstName(createdContractor.getFirstName());
        contractor.setPatronymic(createdContractor.getPatronymic());
        contractor.setLastName(createdContractor.getLastName());
        contractor.setCountry(createdContractor.getCountry());
        contractor.setPhone(createdContractor.getPhone());
        contractor.setEmail(createdContractor.getEmail());
        contractor.setTelegram(createdContractor.getTelegram());
        contractor.setCredential(createdContractor.getCredential());
        contractorRepository.save(contractor);
        return contractor;
    } else {
            return null;
        }
    }

    public Contractor deleteContractor (Long id) {
        Contractor contractor = findContractor(id);
            contractor.setRemoved(true);
            contractor.setDocuments(null);
            contractorRepository.save(contractor);
            return contractor;
        }

}