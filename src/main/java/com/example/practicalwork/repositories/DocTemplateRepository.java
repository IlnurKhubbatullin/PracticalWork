package com.example.practicalwork.repositories;

import com.svbabaya.edms.models.DocTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocTemplateRepository extends JpaRepository<DocTemplate, Long> {
}
