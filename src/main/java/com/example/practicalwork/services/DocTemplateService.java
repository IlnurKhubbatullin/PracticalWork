package com.example.practicalwork.services;

import com.example.practicalwork.DTO.DocTemplateDTO;
import com.example.practicalwork.models.DocTemplate;
import com.example.practicalwork.repositories.DocTemplateRepository;
import com.example.practicalwork.utils.DocTemplateNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class DocTemplateService {

    private final DocTemplateRepository docTemplateRepository;
//    private final DocTemplateDTO docTemplateDTO;

    public List<DocTemplate> findAll() {

        return docTemplateRepository.findAll();
    }

    public List<DocTemplate> findCurrent() {
        return docTemplateRepository.findAll()
                .stream().filter(el -> !el.isRemoved())
                .toList();
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
        DocTemplate docTemplate = read(id);
        docTemplate.setRemoved(true);
        docTemplateRepository.save(docTemplate);
    }

    @Transactional
    public void revive(Long id) {
        DocTemplate docTemplate = read(id);
        docTemplate.setRemoved(false);
        docTemplateRepository.save(docTemplate);
    }

    @Transactional
    public void update(DocTemplate entity) {

        // Change to ModelMapper

        DocTemplate myDocTemplate = read(entity.getId());
        myDocTemplate.setTitle(entity.getTitle());
        myDocTemplate.setVersion(entity.getVersion());
        myDocTemplate.setDocTitle(entity.getDocTitle());

        docTemplateRepository.save(myDocTemplate);

    }

}
