package com.example.gif.form.domain.repository;

import com.example.gif.form.domain.entity.FormField;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormFieldRepository extends JpaRepository<FormField, Long> {
}