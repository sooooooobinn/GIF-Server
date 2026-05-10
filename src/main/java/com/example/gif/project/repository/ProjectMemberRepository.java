package com.example.gif.project.repository;

import com.example.gif.project.entity.Project;
import com.example.gif.project.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {

    @Modifying
    @Query("DELETE FROM ProjectMember pm WHERE pm.project = :project")
    void deleteAllByProject(@Param("project") Project project);
}
