package com.project.petstore.utils;

import com.project.petstore.dto.DogDTO;
import com.project.petstore.dto.HistoryRecordDTO;
import com.project.petstore.dto.PetDTO;
import com.project.petstore.dto.UserDTO;
import com.project.petstore.models.HistoryRecord;
import com.project.petstore.models.Pet;
import com.project.petstore.models.User;

import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    public static UserDTO userToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setBudget(user.getBudget());
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());

        return userDTO;
    }

    public static HistoryRecordDTO historyRecordToHistoryRecordDTO(HistoryRecord historyRecord) {
        HistoryRecordDTO historyRecordDTO = new HistoryRecordDTO();

        historyRecordDTO.setDateExecuted(historyRecord.getDateExecuted());
        historyRecordDTO.setFailCount(historyRecord.getFailCount());
        historyRecordDTO.setSuccessCount(historyRecord.getSuccessCount());

        return historyRecordDTO;
    }

    public static PetDTO petToPetDTO(Pet pet) {
        return pet.toDTO();
    }

    public static List<UserDTO> usersToUsersDTOS(List<User> users) {
        return users.stream().map(Utils::userToUserDTO).collect(Collectors.toList());
    }

    public static List<PetDTO> petsToPetsDTOS(List<Pet> pets) {
        return pets.stream().map(Utils::petToPetDTO).collect(Collectors.toList());
    }

    public static List<HistoryRecordDTO> historyRecordsToHistoryRecordsDTOS(List<HistoryRecord> historyRecords) {
        return historyRecords.stream().map(Utils::historyRecordToHistoryRecordDTO).collect(Collectors.toList());
    }
}
