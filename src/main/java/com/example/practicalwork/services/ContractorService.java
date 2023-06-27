package com.example.practicalwork.services;


import com.example.practicalwork.models.Contractor;
import com.example.practicalwork.repositories.ContractorRepository;
import com.example.practicalwork.utils.contractor.ContrIsDeletedException;
import com.example.practicalwork.utils.contractor.ContrNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ContractorService {
    private final ContractorRepository contractorRepository;

    @Autowired
    public ContractorService(ContractorRepository contractorRepository) {
        this.contractorRepository = contractorRepository;
    }

    public List<Contractor> allContractors() {
        return contractorRepository.findAll();
    }

    public List<Contractor> findCurrent() {
        return contractorRepository.findAll().stream()
                .filter((x -> !x.isRemoved()))
                .toList();
    }

    public Contractor findContractor(Long id) {
        Optional<Contractor> conOpt = contractorRepository.findById(id);
        return conOpt.orElseThrow(ContrNotFoundException::new);
    }

    @Transactional
    public void updateContractor(Contractor updateContractor) {
        Contractor contractor = findContractor(updateContractor.getId());
        contractor.setFirstName(updateContractor.getFirstName());
        contractor.setPatronymic(updateContractor.getPatronymic());
        contractor.setLastName(updateContractor.getLastName());
        contractor.setCountry(updateContractor.getCountry());
        contractor.setPhone(updateContractor.getPhone());
        contractor.setEmail(updateContractor.getEmail());
        contractor.setTelegram(updateContractor.getTelegram());
        contractor.setCredential(updateContractor.getCredential());
        contractorRepository.save(contractor);
    }

    @Transactional
    public void saveCreatedContractor(Contractor createdContractor) {
        contractorRepository.save(createdContractor);
    }

    @Transactional
    public void deleteContractor(Long id) {
        Contractor contractor = findContractor(id);
        if (contractor.isRemoved()) {
            throw new ContrIsDeletedException();
        } else {
            contractor.setRemoved(true);
            contractor.setDocuments(null);
            contractorRepository.save(contractor);
        }
    }

}