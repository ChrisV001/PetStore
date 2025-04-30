package com.project.petstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
@EqualsAndHashCode(callSuper = true)
@Data
public class CatDTO extends PetDTO{

}
