package com.example.practicalwork.models;

public enum Extension {
    DOCX,
    XLSX,
    PDF;
    public static Extension findByValue(String value) {
        for (Extension m : Extension.values()) {
            if (m.name().equalsIgnoreCase(value)) {
                return m;
            }
        }
        return null;
//        throw new DocumentInvalidFormatOfFileException();
    }
}
