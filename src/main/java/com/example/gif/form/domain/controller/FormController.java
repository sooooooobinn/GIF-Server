package com.example.gif.form.domain.controller;

import com.example.gif.form.domain.dto.FormCreateRequest;
import com.example.gif.form.domain.dto.FormResponse;
import com.example.gif.form.domain.dto.FormSubmitRequest;
import com.example.gif.form.domain.entity.Form;
import com.example.gif.form.domain.service.FormService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/form")
public class FormController {

    private final FormService formService;

    @PostMapping
    public void create(@RequestBody FormCreateRequest request) {
        formService.createForm(request);
    }

    @GetMapping("/{formId}")
    public FormResponse get(@PathVariable Long formId) {
        return formService.getForm(formId);
    }

    @PostMapping("/{formId}/submit")
    public void submit(
            @PathVariable Long formId,
            @RequestPart FormSubmitRequest request,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) {
        formService.submit(formId, request, files);
    }
}