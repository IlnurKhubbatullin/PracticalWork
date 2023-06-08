package com.example.practicalwork.services;

import com.example.practicalwork.models.DocFile;
import com.example.practicalwork.repositories.DocFileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class DocFileService {
    private final DocFileRepository docFileRepository;

    public List<DocFile> findAll() {

        return docFileRepository.findAll();
    }


}
