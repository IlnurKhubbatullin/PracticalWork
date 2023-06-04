package com.example.practicalwork.services;

import com.example.practicalwork.models.DocField;
import com.example.practicalwork.models.DocTemplate;
import com.example.practicalwork.repositories.DocTemplateRepository;
import com.example.practicalwork.utils.DocFieldNotDeletedException;
import com.example.practicalwork.utils.DocTemplateErrorResponse;
import com.example.practicalwork.utils.DocTemplateNotDeletedException;
import com.example.practicalwork.utils.DocTemplateNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
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
    public void recovery(Long id) {
        DocTemplate docTemplate = read(id);
        if (!docTemplate.isRemoved()) {
            throw new DocTemplateNotDeletedException();
        } else {
            docTemplate.setRemoved(false);
            docTemplateRepository.save(docTemplate);
        }
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
