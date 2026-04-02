package com.example.gif.form.domain.repository;

import com.example.gif.form.domain.entity.FormAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormAnswerRepository extends JpaRepository<FormAnswer, Long> {
}
