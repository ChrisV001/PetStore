package com.project.petstore.service.impl;

import com.project.petstore.dto.PetDTO;
import com.project.petstore.dto.Response;
import com.project.petstore.dto.UserDTO;
import com.project.petstore.exceptions.CustomException;
import com.project.petstore.models.HistoryRecord;
import com.project.petstore.models.Pet;
import com.project.petstore.models.User;
import com.project.petstore.repository.HistoryRepository;
import com.project.petstore.repository.PetRepository;
import com.project.petstore.repository.UserRepository;
import com.project.petstore.service.UserService;
import com.project.petstore.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PetRepository petRepository;

    private final HistoryRepository historyRepository;

    private static final String[] FIRST_NAMES = {
            "John", "Chris", "Bob", "Dave", "Frank", "Ivan", "Miller", "Davis", "Andrew", "Peter"
    };

    private static final String[] LAST_NAMES = {
            "Smith", "Statham", "Johnson", "Williams", "Jones", "Williamson", "North", "Tuckerson", "Brown", "Davis"
    };

    @Override
    public Response findAllUsers() {
        Response response = new Response();

        try {
            List<User> userList = userRepository.findAll();

            List<UserDTO> userDTOS = Utils.usersToUsersDTOS(userList);

            response.setUserDTOS(userDTOS);
            response.setMessage("Successful");
            response.setStatusCode(200);
        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error listing all users: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response createUsers() {
        Response response = new Response();

        try {
            long existing = userRepository.count();
            int toCreate = (int) Math.max(0, 10 - existing);

            Random rand = new Random();
            List<User> users = new ArrayList<>(toCreate);

            IntStream.range(0, toCreate).forEach(user -> {
                String firstName = FIRST_NAMES[rand.nextInt(FIRST_NAMES.length)];
                String lastName = LAST_NAMES[rand.nextInt(LAST_NAMES.length)];
                String email = firstName.toLowerCase() +
                        "." + lastName.toLowerCase() +
                        rand.nextInt(1000) + "@random.com";
                double budget = 10 + rand.nextDouble() * 90;

                User u = new User();
                u.setFirstName(firstName);
                u.setLastName(lastName);
                u.setEmail(email);
                u.setBudget(budget);
                users.add(u);
            });

            userRepository.saveAll(users);

            List<User> allUser = userRepository.findAll();
            List<UserDTO> userDTOS = Utils.usersToUsersDTOS(allUser);

            response.setUserDTOS(userDTOS);
            response.setMessage("Successful");
            response.setStatusCode(200);
        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error creating users: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response buy() {
        Response response = new Response();

        try {
            List<User> users = userRepository.findAll();

            List<PetDTO> bought = new ArrayList<>();

            AtomicInteger success = new AtomicInteger();
            AtomicInteger fail = new AtomicInteger();

            users.forEach(user -> {
                List<Pet> available = petRepository.findByOwnerIsNull()
                        .stream()
                        .filter(p -> p.getPrice() <= user.getBudget())
                        .toList();

                if (!available.isEmpty()) {
                    Pet pet = available.getFirst();

                    if (petRepository.existsByOwner(user)) {
                        return;
                    }

                    pet.setOwner(user);
                    user.setBudget(user.getBudget() - pet.getPrice());

                    petRepository.save(pet);
                    userRepository.save(user);

                    System.out.println(pet.purchaseMessage(user));

                    bought.add(pet.toDTO());
                    success.getAndIncrement();
                } else {
                    fail.getAndIncrement();
                }
            });

            HistoryRecord historyRecord = new HistoryRecord(LocalDate.now(), success.get(), fail.get());

            historyRepository.save(historyRecord);
            response.setHistoryRecordDTOS(Utils.historyRecordsToHistoryRecordsDTOS(List.of(historyRecord)));

            response.setPetDTOS(bought);
            response.setMessage("Successful");
            response.setStatusCode(200);
        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error buying pets: " + e.getMessage());
        }
        return response;
    }


}
