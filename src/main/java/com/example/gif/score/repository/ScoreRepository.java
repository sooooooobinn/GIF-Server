package com.example.gif.score.repository;

import com.example.gif.project.entity.Project;
import com.example.gif.score.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findByProject(Project project);
}
