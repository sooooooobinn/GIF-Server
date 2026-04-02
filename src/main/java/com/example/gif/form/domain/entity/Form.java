package com.example.gif.form.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Form {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private LocalDate deadline;
    private String createdBy;

    @OneToMany(mappedBy = "form", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<FormField> fields = new ArrayList<>();

    public static Form create(String title, LocalDate deadline, String createdBy) {
        return Form.builder()
                .title(title)
                .deadline(deadline)
                .createdBy(createdBy)
                .build();
    }

    public void addField(FormField field) {
        this.fields.add(field);
        field.setForm(this);
    }

    public void addFields(List<FormField> fields) {
        for (FormField field : fields) {
            addField(field);
        }
    }
}