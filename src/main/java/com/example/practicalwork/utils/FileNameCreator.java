package com.example.practicalwork.utils;

import com.example.practicalwork.models.Document;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class FileNameCreator {
    public String create(Document doc) {

        return doc.getDocTitle().getLabel() +
                "_" +
                doc.getNumber() +
                "_" +
                LocalDateTime.now().toString().substring(0, 10);
    }
}
