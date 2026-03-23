package com.example.gif.domain.form.service;

import com.form.common.auth.MockAuthUser;
import com.form.domain.form.dto.FormCreateRequest;
import com.form.domain.form.dto.FormResponse;
import com.form.domain.form.entity.Form;
import com.form.domain.form.entity.FormField;
import com.form.domain.form.repository.FormRepository;
import com.form.domain.user.entity.User;
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
        // ADMIN 권한 체크
        if (currentUser.getRole() != User.Role.ADMIN) {
            throw new IllegalStateException("양식 생성은 ADMIN만 가능합니다.");
        }

        // Form 생성
        Form form = Form.builder()
                .title(request.getTitle())
                .deadline(request.getDeadline())
                .createdBy(currentUser)
                .build();

        // FormField 생성
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
                .orElseThrow(() -> new IllegalArgumentException("양식을 찾을 수 없습니다."));
        return FormResponse.from(form);
    }
}