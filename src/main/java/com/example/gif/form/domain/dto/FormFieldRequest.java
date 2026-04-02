package com.example.gif.form.domain.dto;

import com.example.gif.form.domain.entity.FieldType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormFieldRequest {
    private String question;
    private FieldType type;
}