package com.example.gif.score.dto.Response;

import lombok.Getter;
import com.example.gif.score.entity.Score;

@Getter
public class ScoreResponseDto {
    private String teamName;
    private Integer totalTechnicalScore;
    private Integer totalSocialValueScore;
    private Integer totalAiUtilityScore;
    private Integer totalPresentationScore;
    private Integer finalTotalScore;
    private int rank;

    public ScoreResponseDto(String teamName, Integer tech, Integer social, Integer ai, Integer pres, Integer total, int rank) {
        this.teamName = teamName;
        this.totalTechnicalScore = tech;
        this.totalSocialValueScore = social;
        this.totalAiUtilityScore = ai;
        this.totalPresentationScore = pres;
        this.finalTotalScore = total;
        this.rank = rank;
    }
}
