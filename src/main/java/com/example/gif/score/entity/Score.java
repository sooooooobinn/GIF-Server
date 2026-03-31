package com.example.gif.score.entity;

import com.example.gif.project.entity.Project;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", unique = true, nullable = false)
    private Project project;

    @Column(name = "technical_score", nullable = false)
    private Integer technicalScore;

    @Column(name = "social_value_score", nullable = false)
    private Integer socialValueScore;

    @Column(name = "ai_utility_score", nullable = false)
    private Integer aiUtilityScore;

    @Column(name = "presentation_score", nullable = false)
    private Integer presentationScore;

    @Column(name = "total_score", nullable = false)
    private Integer totalScore;

    @Column(name = "score_rank")
    private int rank;

    @Builder
    public Score(Project project, Integer technicalScore, Integer socialValueScore,
                 Integer aiUtilityScore, Integer presentationScore) {
        this.project = project;
        this.technicalScore = technicalScore;
        this.socialValueScore = socialValueScore;
        this.aiUtilityScore = aiUtilityScore;
        this.presentationScore = presentationScore;
        calculateTotalScore();
    }

    public void updateScore(Integer technicalScore, Integer socialValueScore,
                            Integer aiUtilityScore, Integer presentationScore) {
        this.technicalScore = technicalScore;
        this.socialValueScore = socialValueScore;
        this.aiUtilityScore = aiUtilityScore;
        this.presentationScore = presentationScore;
        calculateTotalScore();
    }

    public void updateRank(int rank) {
        this.rank = rank;
    }

    private void calculateTotalScore() {
        this.totalScore = this.technicalScore + this.socialValueScore +
                this.aiUtilityScore + this.presentationScore;
    }
}