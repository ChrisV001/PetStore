package com.project.petstore.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class DogDTO extends PetDTO {

    private Integer rating;

}
