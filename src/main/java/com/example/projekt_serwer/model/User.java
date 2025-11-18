package com.example.projekt_serwer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @Email
    @Column(unique = true)
    private String email;

    private Integer status;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "user")
    private Set<UserTrainingPlan> userTrainingPlans;

    @OneToMany(mappedBy = "createdBy")
    private Set<TrainingPlan> createdTrainingPlans;
}