package com.example.practicalwork.repositories;

import com.example.practicalwork.models.Contractor;
import com.example.practicalwork.models.Credential;
import com.example.practicalwork.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractorRepository extends JpaRepository <Contractor, Long> {

//    List <Contractor> findAllContractors ();
//    Contractor findContractorById (Long id);
//
//    Contractor findContractorByDocuments (List<Document> documents);
//
//    Contractor findContractorByFilter (String firstName, String patronymic, String lastName, String country, Credential credential);


}
