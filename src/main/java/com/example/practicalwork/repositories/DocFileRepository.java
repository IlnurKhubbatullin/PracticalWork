package com.example.practicalwork.repositories;

import com.example.practicalwork.models.DocFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocFileRepository extends JpaRepository<DocFile, Long> {

}
