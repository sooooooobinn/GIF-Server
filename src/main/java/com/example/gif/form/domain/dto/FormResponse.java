package com.example.gif.form.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class FormResponse {

    private Long id;
    private String title;
    private LocalDate deadline;
    private String createdBy;
    private List<FieldResponse> fields;

    @Getter
    @Builder
    public static class FieldResponse {
        private Long id;
        private String question;
        private String type;
    }
}
