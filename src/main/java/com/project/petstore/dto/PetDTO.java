package com.project.petstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PetDTO {

    private Long id;

    private String name;

    private String description;

    private LocalDate birthDate;

    private double price;

    private UserDTO owner;
}
