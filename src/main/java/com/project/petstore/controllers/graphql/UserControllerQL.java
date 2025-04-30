package com.project.petstore.controllers.graphql;

import com.project.petstore.dto.PetDTO;
import com.project.petstore.dto.UserDTO;
import com.project.petstore.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserControllerQL {

    private final UserService userService;

    @QueryMapping
    public List<UserDTO> listAllUsers() {
        return userService.findAllUsers().getUserDTOS();
    }

    @MutationMapping
    public List<UserDTO> createUsers() {
        return userService.createUsers().getUserDTOS();
    }

    @MutationMapping
    public List<PetDTO> buyPets() {
        return userService.buy().getPetDTOS();
    }
}
