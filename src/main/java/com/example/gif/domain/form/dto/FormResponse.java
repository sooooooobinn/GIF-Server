package com.example.gif.domain.form.dto;

import com.example.gif.domain.form.entity.FieldStyle;
import com.example.gif.domain.form.entity.Form;
import com.example.gif.domain.form.entity.FormField;
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
        private String title;
        private String description;
        private FieldStyle style;
        private int orderIndex;
    }

    public static FormResponse from(Form form) {
        List<FieldResponse> fieldResponses = form.getFields().stream()
                .map(f -> FieldResponse.builder()
                        .id(f.getId())
                        .title(f.getTitle())
                        .description(f.getDescription())
                        .style(f.getStyle())
                        .orderIndex(f.getOrderIndex())
                        .build())
                .toList();

        return FormResponse.builder()
                .id(form.getId())
                .title(form.getTitle())
                .deadline(form.getDeadline())
                .createdBy(form.getCreatedBy().getName())
                .fields(fieldResponses)
                .build();
    }
}