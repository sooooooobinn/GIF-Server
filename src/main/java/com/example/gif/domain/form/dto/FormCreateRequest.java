package com.example.gif.domain.form.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class FormCreateRequest {

    @NotBlank(message = "목은 필수입니다.")
    private String title;

    @NotNull(message = "마감일은 필수입니다.")
    private LocalDate deadline;

    @Valid
    private List<FormFieldRequest> fields;
}