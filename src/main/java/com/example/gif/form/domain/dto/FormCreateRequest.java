package com.example.gif.form.domain.dto;

import com.example.gif.form.domain.entity.FieldType;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class FormCreateRequest {
    private String title;
    private LocalDate deadline;
    private List<FieldDto> fields;

    @Getter
    public static class FieldDto {
        private String question;
        private FieldType type;
    }
}
