package com.example.practicalwork.utils;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class FileUriResponse {

    private String uri;
    private LocalDateTime timestamp;

    public FileUriResponse() {

        timestamp = LocalDateTime.now();
    }
}
