package com.project.petstore.models;

import com.project.petstore.dto.CatDTO;
import com.project.petstore.dto.PetDTO;
import com.project.petstore.dto.UserDTO;
import com.project.petstore.utils.Utils;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@Entity
@DiscriminatorValue("CAT")
@NoArgsConstructor
public class Cat extends Pet {
    @Override
    public double getPrice() {
        return Period.between(getBirthDate(), LocalDate.now()).getYears();
    }

    @Override
    public PetDTO toDTO() {
        CatDTO catDTO = new CatDTO();
        catDTO.setId(getId());
        catDTO.setName(getName());
        catDTO.setPrice(getPrice());
        catDTO.setBirthDate(getBirthDate());
        if (getOwner() != null) {
            UserDTO userDTO = Utils.userToUserDTO(getOwner());
            catDTO.setOwner(userDTO);
        }
        return catDTO;
    }

    @Override
    public String purchaseMessage(User buyer) {
        return String.format("Meow, cat %s has owner %s", getName(), buyer.getFirstName());
    }
}
