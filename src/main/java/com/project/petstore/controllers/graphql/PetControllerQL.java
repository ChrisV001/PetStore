package com.project.petstore.controllers.graphql;

import com.project.petstore.dto.PetDTO;
import com.project.petstore.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PetControllerQL {

    private final PetService petService;

    @QueryMapping
    public List<PetDTO> listAllPets() {
        return petService.findAllPets().getPetDTOS();
    }

    @MutationMapping
    public List<PetDTO> createPets() {
        return petService.createPets().getPetDTOS();
    }
}
