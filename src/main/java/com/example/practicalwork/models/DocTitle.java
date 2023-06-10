package com.example.practicalwork.models;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum DocTitle implements Serializable {
    CONTRACT("Contract"),
    AGREEMENT("Agreement"),
    APPLICATION("Application"),
    ACT("Act"),
    REFERENCE("Reference");

    private final String label;

    DocTitle(String label) {
        this.label = label;
    }

    public static String findByValue(String value) {
        for (DocTitle type : DocTitle.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type.name();
            }
        }
//        throw new DocTemplateUnknownTypeOfDocException();
        return null;
    }
}
