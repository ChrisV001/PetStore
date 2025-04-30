package com.project.petstore.service.impl;

import com.project.petstore.dto.PetDTO;
import com.project.petstore.dto.Response;
import com.project.petstore.exceptions.CustomException;
import com.project.petstore.models.Cat;
import com.project.petstore.models.Dog;
import com.project.petstore.models.Pet;
import com.project.petstore.models.User;
import com.project.petstore.repository.PetRepository;
import com.project.petstore.repository.UserRepository;
import com.project.petstore.service.PetService;
import com.project.petstore.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    private final UserRepository userRepository;

    private static final String[] PET_NAMES = {
            "Bella", "Max", "Ben", "Luna", "Daisy", "Hector", "Jack", "Jax", "Kenzi", "Kiva"
    };

    private static final String[] DESCRIPTION = {
            "Playful","Shy","Friendly","Curious","Energetic","High Alert","Respectful"
    };

    @Override
    public Response findAllPets() {
        Response response = new Response();

        try {
            List<Pet> pets = petRepository.findAll();

            List<PetDTO> petDTOS = Utils.petsToPetsDTOS(pets);

            response.setPetDTOS(petDTOS);
            response.setMessage("Successful");
            response.setStatusCode(200);
        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting all pets: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response createPets() {
        Response response = new Response();

        try {
            long existing = petRepository.count();
            int toCreate = (int) Math.max(0, 20 - existing);

            Random rand = new Random();
            List<Pet> pets = new ArrayList<>(toCreate);
            List<User> allUsers = userRepository.findAll();

            IntStream.range(0, toCreate).forEach(petIndex -> {
                LocalDate dob = LocalDate.now()
                        .minusYears(rand.nextInt(10))
                        .minusDays(rand.nextInt(365));

                String name = PET_NAMES[rand.nextInt(PET_NAMES.length)];
                String desc = DESCRIPTION[rand.nextInt(DESCRIPTION.length)];

                Pet pet;
                if (rand.nextBoolean()) {
                    Dog dog = new Dog();
                    dog.setRating(rand.nextInt(11)); // 0 - 10
                    pet = dog;
                } else {
                    pet = new Cat();
                }

                pet.setName(name);
                pet.setDescription(desc);
                pet.setBirthDate(dob);

                pets.add(pet);
            });

            if (!pets.isEmpty()) {
                petRepository.saveAll(pets);
            }

            List<PetDTO> petDTOS = Utils.petsToPetsDTOS(pets);
            response.setPetDTOS(petDTOS);
            response.setMessage("Successful");
            response.setStatusCode(200);
        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error creating pets: " + e.getMessage());
        }
        return response;
    }
}
