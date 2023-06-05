package com.example.practicalwork.utils;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
public class DocTemplateErrorResponse {
        private String message;
        private LocalDateTime timestamp;

        public DocTemplateErrorResponse() {

                timestamp = LocalDateTime.now();
        }

}

