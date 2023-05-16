package com.example.practicalwork.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class ContractorDTO {
    private String firstName;

    private String patronymic;

    private String lastName;

    private String email;

    private String telegram;
}
