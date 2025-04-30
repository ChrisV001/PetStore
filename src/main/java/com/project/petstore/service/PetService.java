package com.project.petstore.service;

import com.project.petstore.dto.Response;

public interface PetService {
    Response findAllPets();
    //functions cannot be named with a -
    Response createPets();
}
