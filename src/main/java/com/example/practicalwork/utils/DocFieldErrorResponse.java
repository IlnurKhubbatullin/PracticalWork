package com.example.practicalwork.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class DocFieldErrorResponse {
        private String message;
        @CreationTimestamp
        private LocalDateTime createdAt;
}

