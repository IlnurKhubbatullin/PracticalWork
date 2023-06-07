package com.example.practicalwork.converters;

import com.example.practicalwork.models.Document;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ToZipConverter {
    public void convert(Document doc) {
        System.out.println("Converted to zip!");
    }
}
