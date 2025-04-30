package com.project.petstore.controllers;

import com.project.petstore.dto.Response;
import com.project.petstore.service.PetService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pet")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @GetMapping("/all")
    public ResponseEntity<Response> getAllPets() {
        Response response = petService.findAllPets();
        return ResponseEntity.status(response.getStatusCode())
                .body(response);
    }

    @GetMapping("/create-pets")
    public ResponseEntity<Response> createPets() {
        Response response = petService.createPets();
        return ResponseEntity.status(response.getStatusCode())
                .body(response);
    }

}
