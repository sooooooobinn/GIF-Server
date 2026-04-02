package com.example.gif.form.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;
    private String answer;
    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "submission_id")
    private FormSubmission submission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id")
    private FormField field;

    public static FormAnswer create(String answer, String fileUrl, FormSubmission submission, FormField field) {
        return FormAnswer.builder()
                .answer(answer)
                .fileUrl(fileUrl)
                .submission(submission)
                .field(field)
                .build();
    }
}
