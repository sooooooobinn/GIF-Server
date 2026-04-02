package com.example.gif.form.domain.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class FormSubmitRequest {

    private Long userId;

    private List<AnswerDto> answers;

    @Getter
    public static class AnswerDto {
        private Long fieldId;
        private String answer;
    }
}
