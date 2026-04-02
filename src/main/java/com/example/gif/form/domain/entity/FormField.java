package com.example.gif.form.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class FormField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String question;

    @Enumerated(EnumType.STRING)
    private FieldType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id")
    private Form form;

    public static FormField create(String question, FieldType type) {
        return FormField.builder()
                .question(question)
                .type(type)
                .build();
    }

    public void setForm(Form form) {
        this.form = form;
    }
}
