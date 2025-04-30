package com.project.petstore;

import com.project.petstore.dto.PetDTO;
import com.project.petstore.dto.Response;
import com.project.petstore.models.Cat;
import com.project.petstore.models.Pet;
import com.project.petstore.models.User;
import com.project.petstore.repository.PetRepository;
import com.project.petstore.repository.UserRepository;
import com.project.petstore.service.PetService;
import com.project.petstore.service.impl.PetServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private UserRepository userRepository;

    private PetService petService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        petService = new PetServiceImpl(petRepository, userRepository);
    }

    @Test
    void findAllPets() {
        Cat cat = new Cat();
        cat.setId(5L);
        cat.setName("Ben");
        cat.setBirthDate(LocalDate.now().minusYears(3));
        when(petRepository.findAll()).thenReturn(List.of(cat));

        Response response = petService.findAllPets();
        assertThat(response.getStatusCode()).isEqualTo(200);
        List<PetDTO> petDTOS = response.getPetDTOS();
        assertThat(petDTOS).hasSize(1);
        assertThat(petDTOS.getFirst().getId()).isEqualTo(5L);
        assertThat(petDTOS.getFirst().getName()).isEqualTo("Ben");
        assertThat(petDTOS.getFirst().getOwner()).isNull();
        assertThat(petDTOS.getFirst().getDescription()).isNull();
        assertThat(petDTOS.getFirst().getBirthDate()).isEqualTo(LocalDate.now().minusYears(3));
    }

    @Test
    void createPets() {
        when(petRepository.count()).thenReturn(15L);
        when(petRepository.saveAll(anyList()))
                .thenAnswer(i -> i.getArgument(0));
        List<Pet> allPets = new ArrayList<>();
        allPets.addAll(Collections.nCopies(20, new Cat()));
        when(petRepository.findAll()).thenReturn(allPets);

        Response response = petService.createPets();
        verify(petRepository).saveAll(argThat((List<Pet> pets) -> pets.size() == 5));
        assertThat(response.getStatusCode()).isEqualTo(200);
        List<PetDTO> petDTOS = response.getPetDTOS();
        assertThat(petDTOS).hasSize(5);
    }
}
