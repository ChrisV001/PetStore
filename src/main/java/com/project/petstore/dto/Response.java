package com.project.petstore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private int statusCode;

    private String message;

    private UserDTO userDTO;

    private PetDTO petDTO;

    private CatDTO catDTO;

    private DogDTO dogDTO;

    private List<UserDTO> userDTOS;

    private List<PetDTO> petDTOS;

    private List<HistoryRecordDTO> historyRecordDTOS;
}
