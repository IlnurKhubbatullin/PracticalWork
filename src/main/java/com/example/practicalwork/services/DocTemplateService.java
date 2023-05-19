package com.example.practicalwork.services;

import com.example.practicalwork.DTO.DocTemplateDTO;
import com.example.practicalwork.models.DocTemplate;
import com.example.practicalwork.repositories.DocTemplateRepository;
import com.example.practicalwork.utils.DocTemplateNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DocTemplateService {

    private final DocTemplateRepository docTemplateRepository;

    public DocTemplateService(DocTemplateRepository docTemplateRepository) {
        this.docTemplateRepository = docTemplateRepository;
    }

    public List<DocTemplate> findAll() {

        return docTemplateRepository.findAll();
    }

    public DocTemplate read(Long id) {
        Optional<DocTemplate> foundDocument = docTemplateRepository.findById(id);
        return foundDocument.orElseThrow(DocTemplateNotFoundException::new);
    }

    @Transactional
    public void create(DocTemplate docTemplate) {
        docTemplateRepository.save(docTemplate);
    }

    @Transactional
    public void delete(Long id) {
        read(id).setRemoved(true);
    }

    @Transactional
    public void update(DocTemplateDTO dto) {

        // Change to ModelMapper

        DocTemplate myDocTemplate = read(dto.getId());
        myDocTemplate.setTitle(dto.getTitle());
        myDocTemplate.setVersion(dto.getVersion());

        docTemplateRepository.save(myDocTemplate);

    }
}
