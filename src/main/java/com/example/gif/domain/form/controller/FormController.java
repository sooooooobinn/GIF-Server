package com.example.gif.domain.form.controller;

import com.example.gif.common.auth.MockAuthUser;
import com.example.gif.domain.form.dto.FormCreateRequest;
import com.example.gif.domain.form.dto.FormResponse;
import com.example.gif.domain.form.service.FormService;
import com.example.gif.domain.form.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/form")
@RequiredArgsConstructor
public class FormController {

    private final FormService formService;

   // Oauth 완료되면 교체하기
    @PostMapping
    public ResponseEntity<FormResponse> createForm(
            @Valid @RequestBody FormCreateRequest request
    ) {
        User currentUser = MockAuthUser.getAdminUser();
        FormResponse response = formService.createForm(request, currentUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{formId}")
    public ResponseEntity<FormResponse> getForm(@PathVariable Long formId) {
        return ResponseEntity.ok(formService.getForm(formId));
    }
}
