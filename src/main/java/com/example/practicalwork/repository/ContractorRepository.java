package com.example.practicalwork.repository;

import com.example.practicalwork.models.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContractorRepository extends JpaRepository <Contractor, Long> {

    List <Contractor> findAllContractors ();
    Optional <Contractor> findContractorById (Long id);

    Optional <Contractor> findContractorByDocuments (Documents);


}
