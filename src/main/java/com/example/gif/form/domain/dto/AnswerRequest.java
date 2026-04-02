package com.example.gif.form.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerRequest {
    private Long fieldId;
    private String answer;
}
