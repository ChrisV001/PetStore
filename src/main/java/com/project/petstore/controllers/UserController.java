package com.project.petstore.controllers;

import com.project.petstore.dto.Response;
import com.project.petstore.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<Response> getAllUsers() {
        Response response = userService.findAllUsers();
        return ResponseEntity.status(response.getStatusCode())
                .body(response);
    }

    @GetMapping("/create-users")
    public ResponseEntity<Response> createUsers() {
        Response response = userService.createUsers();
        return ResponseEntity.status(response.getStatusCode())
                .body(response);
    }

    @GetMapping("/buy-pets")
    public ResponseEntity<Response> buyPetsForUsers() {
        Response response = userService.buy();
        return ResponseEntity.status(response.getStatusCode())
                .body(response);
    }

}
