package com.example.practicalwork.utils;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
public class DocumentErrorResponse {
        private String message;
        private LocalDateTime timestamp;

        public DocumentErrorResponse() {

                timestamp = LocalDateTime.now();
        }

}

