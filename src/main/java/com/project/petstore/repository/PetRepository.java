package com.project.petstore.repository;

import com.project.petstore.models.Pet;
import com.project.petstore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository <Pet, Long> {
    List<Pet> findByOwnerIsNull();

    boolean existsByOwner(User owner);
}
