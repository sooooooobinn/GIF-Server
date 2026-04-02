package com.example.gif.form.domain.repository;

import com.example.gif.form.domain.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormRepository extends JpaRepository<Form, Long> {
}
