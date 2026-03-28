package com.example.gif.domain.form.service;

import com.example.gif.domain.form.dto.FormCreateRequest;
import com.example.gif.domain.form.dto.FormResponse;
import com.example.gif.domain.form.entity.Form;
import com.example.gif.domain.form.entity.FormField;
import com.example.gif.domain.form.repository.FormRepository;
import com.example.gif.domain.form.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormService {

    private final FormRepository formRepository;

    @Transactional
    public FormResponse createForm(FormCreateRequest request, User currentUser) {

        Form form = Form.builder()
                .title(request.getTitle())
                .deadline(request.getDeadline())
                .createdBy(currentUser)
                .build();

        if (request.getFields() != null) {
            List<FormField> fields = request.getFields().stream()
                    .map(f -> FormField.builder()
                            .title(f.getTitle())
                            .description(f.getDescription())
                            .style(f.getStyle())
                            .orderIndex(f.getOrderIndex())
                            .form(form)
                            .build())
                    .toList();
            form.getFields().addAll(fields);
        }

        Form saved = formRepository.save(form);
        return FormResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public FormResponse getForm(Long formId) {
        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("양식을 찾을 수 없습니다."));
        return FormResponse.from(form);
    }
}