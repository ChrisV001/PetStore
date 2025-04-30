package com.project.petstore.service;

import com.project.petstore.dto.Response;

public interface UserService {
    Response findAllUsers();

    Response createUsers();

    Response buy();
}
