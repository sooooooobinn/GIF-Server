package com.example.gif.domain.form.dto;

import com.example.gif.domain.form.entity.FieldStyle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class FormFieldRequest {

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    private String description;

    @NotNull(message = "스타일을 선택해주세요.")
    private FieldStyle style; // CALENDAR / TEXT / FILE

    private int orderIndex;
}
