package com.example.gif.form.domain.repository;

import com.example.gif.form.domain.entity.FormSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormSubmissionRepository extends JpaRepository<FormSubmission, Long> {}
