package com.project.petstore.models;

import com.project.petstore.dto.PetDTO;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

import static jakarta.persistence.InheritanceType.SINGLE_TABLE;

@Entity
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "pet_type")
@Data
@Getter
public abstract class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User owner;

    private String name;

    private String description;

    private LocalDate birthDate;

    public abstract double getPrice();

    public abstract PetDTO toDTO();

    public abstract String purchaseMessage(User buyer);
}
