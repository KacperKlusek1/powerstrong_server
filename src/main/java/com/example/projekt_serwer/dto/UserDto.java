package com.example.projekt_serwer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id;
    private String username;
    private String email;
    private Integer status;
    private String createDate;
    private Integer completedTrainingPlansCount;
    private Integer createdTrainingPlansCount;
}