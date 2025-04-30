package com.project.petstore;

import com.project.petstore.dto.PetDTO;
import com.project.petstore.dto.Response;
import com.project.petstore.dto.UserDTO;
import com.project.petstore.models.Cat;
import com.project.petstore.models.HistoryRecord;
import com.project.petstore.models.Pet;
import com.project.petstore.models.User;
import com.project.petstore.repository.HistoryRepository;
import com.project.petstore.repository.PetRepository;
import com.project.petstore.repository.UserRepository;
import com.project.petstore.service.UserService;
import com.project.petstore.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private HistoryRepository historyRepository;

    private UserService userService;


    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);

        userService = new UserServiceImpl(userRepository, petRepository, historyRepository);
    }

    @Test
    void findAllUsers() {
        User u = new User();
        u.setId(1L);
        u.setFirstName("Frank");
        u.setLastName("Vinci");
        u.setEmail("frankvinci11@example.com");
        u.setBudget(50.0);
        when(userRepository.findAll()).thenReturn(List.of(u));

        Response response = userService.findAllUsers();
        assertThat(response.getStatusCode()).isEqualTo(200);
        List<UserDTO> userDTOS = response.getUserDTOS();
        assertThat(userDTOS).hasSize(1)
                .first()
                .extracting(UserDTO::getId, UserDTO::getBudget)
                .containsExactly(1L, 50.0);
    }

    @Test
    void createUsers() {
        when(userRepository.count()).thenReturn(7L);
        when(userRepository.saveAll(anyList()))
                .thenAnswer(i -> i.getArgument(0));
        List<User> after = new ArrayList<>();
        after.addAll(Collections.nCopies(7, new User()));
        after.addAll(Collections.nCopies(3, new User()));
        when(userRepository.findAll()).thenReturn(after);

        Response response = userService.createUsers();
        verify(userRepository).saveAll(argThat((List<User> list) -> list.size() == 3));
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getUserDTOS()).hasSize(10);
    }

    @Test
    void buyPetsSuccessfulPurchase() {
        User user = new User();
        user.setId(1L);
        user.setBudget(100.0);
        when(userRepository.findAll()).thenReturn(List.of(user));

        Cat cat = new Cat();
        cat.setId(10L);
        cat.setBirthDate(LocalDate.now().minusYears(1));
        when(petRepository.findByOwnerIsNull()).thenReturn(List.of(cat));

        when(petRepository.save(any(Pet.class)))
                .thenAnswer(i -> i.getArgument(0));
        when(userRepository.save(any(User.class)))
                .thenAnswer(i -> i.getArgument(0));
        when(historyRepository.save(any(HistoryRecord.class)))
                .thenAnswer(i -> i.getArgument(0));

        Response response = userService.buy();
        assertThat(response.getStatusCode()).isEqualTo(200);
        List<PetDTO> boughtPets = response.getPetDTOS();
        assertThat(boughtPets).hasSize(1);

        PetDTO petDTO = boughtPets.getFirst();

        assertThat(petDTO.getId()).isEqualTo(10L);
        assertThat(petDTO.getPrice()).isEqualTo(cat.getPrice());
        assertThat(petDTO.getOwner().getId()).isEqualTo(1L);
        assertThat(petDTO.getDescription()).isNull();

        verify(petRepository).save(cat);
        verify(userRepository).save(user);
        verify(historyRepository).save(any());
    }

    @Test
    void buyPetsNoAvailablePets() {
        User user = new User();
        user.setId(2L);
        user.setBudget(0.0);
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(petRepository.findByOwnerIsNull()).thenReturn(List.of());

        Response response = userService.buy();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getPetDTOS()).isEmpty();
        verify(historyRepository).save(any());
    }
}
