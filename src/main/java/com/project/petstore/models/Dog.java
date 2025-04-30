package com.project.petstore.models;

import com.project.petstore.dto.CatDTO;
import com.project.petstore.dto.DogDTO;
import com.project.petstore.dto.PetDTO;
import com.project.petstore.dto.UserDTO;
import com.project.petstore.utils.Utils;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

import java.time.LocalDate;
import java.time.Period;


@Entity
@DiscriminatorValue("DOG")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Dog extends Pet {

    @Min(0)
    @Max(10)
    private int rating;

    @Override
    public double getPrice() {
        return Period.between(getBirthDate(), LocalDate.now()).getYears() + rating;
    }

    @Override
    public PetDTO toDTO() {
        DogDTO dogDTO = new DogDTO();

        dogDTO.setId(getId());
        dogDTO.setRating(getRating());
        dogDTO.setName(getName());
        dogDTO.setDescription(getDescription());
        dogDTO.setBirthDate(getBirthDate());
        if (getOwner() != null) {
            UserDTO userDTO = Utils.userToUserDTO(getOwner());
            dogDTO.setOwner(userDTO);
        }
        return dogDTO;
    }

    @Override
    public String purchaseMessage(User buyer) {
        return String.format("Woof, dog %s has owner %s", getName(), buyer.getFirstName());
    }
}
