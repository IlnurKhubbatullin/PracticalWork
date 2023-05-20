package com.example.practicalwork.services;

import com.example.practicalwork.DTO.DocumentDTO;
import com.example.practicalwork.models.Document;
import com.example.practicalwork.repositories.DocRepository;
import com.example.practicalwork.utils.DocNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DocumentService {
    private final DocRepository docRepository;
//    private final DocumentDTO documentDTO;
    public DocumentService(DocRepository docRepository) {
        this.docRepository = docRepository;
//        this.documentDTO = documentDTO;
    }
    public List<Document> findAll() {

        return docRepository.findAll();
    }
    public Document read(Long id) {
        Optional<Document> foundDocument = docRepository.findById(id);
        return foundDocument.orElseThrow(DocNotFoundException::new);
    }
    @Transactional
    public void create(Document document) {

        docRepository.save(document);
    }
    @Transactional
    public void delete(Long id) {
        Document doc = read(id);
        doc.setRemoved(true);
        docRepository.save(doc);
    }
    @Transactional
    public void revive(Long id) {
        Document doc = read(id);
        doc.setRemoved(false);
        docRepository.save(doc);
    }
    @Transactional
    public void update(DocumentDTO dto) {

        // Change to ModelMapper

        Document myDoc = read(dto.getId());
        myDoc.setNumber(dto.getNumber());
        myDoc.setDocTitle(dto.getDocTitle());
        docRepository.save(myDoc);

    }

}
