package com.example.gif.score.dto.Response;

import lombok.Getter;

@Getter
public class ScoreResponseDto {
    private String teamName;
    private Integer avgTechnicalScore;
    private Integer avgSocialValueScore;
    private Integer avgAiUtilityScore;
    private Integer avgPresentationScore;
    private Integer finalTotalScore;

    public ScoreResponseDto(String teamName, Integer tech, Integer social, Integer ai, Integer pres, Integer total) {
        this.teamName = teamName;
        this.avgTechnicalScore = tech;
        this.avgSocialValueScore = social;
        this.avgAiUtilityScore = ai;
        this.avgPresentationScore = pres;
        this.finalTotalScore = total;
    }
}
