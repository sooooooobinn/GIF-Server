package com.example.gif.score.dto.Response;

import lombok.Getter;
import com.example.gif.score.entity.Score;

@Getter
public class ScoreResponseDto {
    private String teamName;
    private Integer technicalScore;
    private Integer socialValueScore;
    private Integer aiUtilityScore;
    private Integer presentationScore;
    private Integer totalScore;
    private int rank;

    public ScoreResponseDto(Score score) {
        this.teamName = score.getProject().getTeamName();
        this.technicalScore = score.getTechnicalScore();
        this.socialValueScore = score.getSocialValueScore();
        this.aiUtilityScore = score.getAiUtilityScore();
        this.presentationScore = score.getPresentationScore();
        this.totalScore = score.getTotalScore();
        this.rank = score.getRank();
    }
}
