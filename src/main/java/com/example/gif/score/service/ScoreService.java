package com.example.gif.score.service;

import com.example.gif.project.entity.Project;
import com.example.gif.project.repository.ProjectRepository;
import com.example.gif.score.dto.Request.ScoreRequestDto;
import com.example.gif.score.dto.Response.RankResponseDto;
import com.example.gif.score.dto.Response.ScoreResponseDto;
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
        Project project = findProject(dto.getProjectId());

        if (scoreRepository.existsByProjectAndEvaluatorId(project, dto.getEvaluatorId())) {
            throw new IllegalStateException("이미 점수를 부여한 팀입니다. 수정 기능을 이용하세요.");
        }

        Score score = Score.builder()
                .project(project)
                .evaluatorId(dto.getEvaluatorId())
                .technicalScore(dto.getTechnicalScore())
                .socialValueScore(dto.getSocialValueScore())
                .aiUtilityScore(dto.getAiUtilityScore())
                .presentationScore(dto.getPresentationScore())
                .build();
        scoreRepository.save(score);
    }

    @Transactional
    public void updateScore(ScoreRequestDto dto) {
        Project project = findProject(dto.getProjectId());

        Score score = scoreRepository.findByProjectAndEvaluatorId(project, dto.getEvaluatorId())
                .orElseThrow(() -> new IllegalArgumentException("수정할 기존 점수 기록이 없습니다."));

        score.updateScore(
                dto.getTechnicalScore(),
                dto.getSocialValueScore(),
                dto.getAiUtilityScore(),
                dto.getPresentationScore()
        );
    }

    public List<ScoreResponseDto> getAllScores() {
        List<Score> allScores = scoreRepository.findAll();
        Map<Project, List<Score>> projectGroup = allScores.stream()
                .collect(Collectors.groupingBy(Score::getProject));

        return projectGroup.entrySet().stream()
                .map(entry -> calculateAverage(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public List<RankResponseDto> getFinalRanking() {
        List<Score> allScores = scoreRepository.findAll();

        Map<Project, List<Score>> projectGroup = allScores.stream()
                .collect(Collectors.groupingBy(Score::getProject));

        List<RankResponseDto> rankingList = projectGroup.entrySet().stream()
                .map(entry -> {
                    Project project = entry.getKey();
                    List<Score> scores = entry.getValue();
                    int count = scores.size();

                    double avgTech = scores.stream().mapToInt(Score::getTechnicalScore).average().orElse(0);
                    double avgSocial = scores.stream().mapToInt(Score::getSocialValueScore).average().orElse(0);
                    double avgAi = scores.stream().mapToInt(Score::getAiUtilityScore).average().orElse(0);
                    double avgPres = scores.stream().mapToInt(Score::getPresentationScore).average().orElse(0);

                    double finalTotal = avgTech + avgSocial + avgAi + avgPres;

                    return new RankResponseDto(
                            project.getTeamName(),
                            (int) Math.round(finalTotal)
                    );
                })
                .sorted(Comparator.comparingInt(RankResponseDto::getTotalScore).reversed())
                .collect(Collectors.toList());

        for (int i = 0; i < rankingList.size(); i++) {
            rankingList.get(i).assignRank(i + 1);
        }

        return rankingList;
    }

    private Project findProject(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 프로젝트 ID 입니다."));
    }

    private ScoreResponseDto calculateAverage(Project project, List<Score> scores) {
        int count = scores.size();

        if (count == 0) {
            return new ScoreResponseDto(project.getTeamName(), 0, 0, 0, 0, 0);
        }

        int avgTech = (int) scores.stream().mapToInt(Score::getTechnicalScore).sum() / count;
        int avgSocial = (int) scores.stream().mapToInt(Score::getSocialValueScore).sum() / count;
        int avgAi = (int) scores.stream().mapToInt(Score::getAiUtilityScore).sum() / count;
        int avgPres = (int) scores.stream().mapToInt(Score::getPresentationScore).sum() / count;

        int finalTotal = avgTech + avgSocial + avgAi + avgPres;

        return new ScoreResponseDto(
                project.getTeamName(),
                avgTech,
                avgSocial,
                avgAi,
                avgPres,
                finalTotal
        );
    }
}