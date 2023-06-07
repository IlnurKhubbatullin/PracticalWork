package com.example.practicalwork.models;

import com.example.practicalwork.utils.DocumentInvalidFormatOfFileException;

public enum Mimetype {
    DOCX,
    XLSX,
    PDF;
    public static Mimetype findByValue(String value) {
        for (Mimetype m : Mimetype.values()) {
            if (m.name().equalsIgnoreCase(value)) {
                return m;
            }
        }
        return null;
//        throw new DocumentInvalidFormatOfFileException();
    }
}
