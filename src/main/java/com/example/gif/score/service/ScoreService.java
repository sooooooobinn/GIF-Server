package com.example.gif.score.service;

import com.example.gif.project.entity.Project;
import com.example.gif.project.repository.ProjectRepository;
import com.example.gif.score.dto.Request.ScoreRequestDto;
import com.example.gif.score.dto.Response.RankResponseDto;
import com.example.gif.score.entity.Score;
import com.example.gif.score.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public void saveScore(ScoreRequestDto dto) {
        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 프로젝트 ID입니다."));

        Score score = Score.builder()
                .project(project)
                .technicalScore(dto.getTechnicalScore())
                .socialValueScore(dto.getSocialValueScore())
                .aiUtilityScore(dto.getAiUtilityScore())
                .presentationScore(dto.getPresentationScore())
                .build();

        scoreRepository.save(score);
    }

    public List<RankResponseDto> getFinalRanking() {
        List<Score> allScores = scoreRepository.findAll();

        Map<Project, Integer> projectScoreMap = allScores.stream()
                .collect(Collectors.groupingBy(
                        Score::getProject,
                        Collectors.summingInt(Score::getSubTotalScore)
                ));

        List<RankResponseDto> rankingList = projectScoreMap.entrySet().stream()
                .map(entry -> new RankResponseDto(
                        entry.getKey().getTeamName(),
                        entry.getValue()
                ))
                .sorted(Comparator.comparingInt(RankResponseDto::getTotalScore).reversed())
                .collect(Collectors.toList());

        for (int i = 0; i < rankingList.size(); i++) {
            rankingList.get(i).assignRank(i + 1);
        }

        return rankingList;
    }
}