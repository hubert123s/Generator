package com.example.generator.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class GeneratorParameters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Min(value = 1, message = "Min value must be at least 1")
    private int min;
    private int max;
    private Long numbersOfString;
    @NotBlank
    private String chars;
    @Enumerated(EnumType.STRING)
    private Status status = Status.WAITING;


}
