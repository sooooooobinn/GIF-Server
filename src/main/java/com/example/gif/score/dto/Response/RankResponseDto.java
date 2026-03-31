package com.example.gif.score.dto.Response;

import com.example.gif.score.entity.Score;
import lombok.Getter;

@Getter
public class RankResponseDto {
    private int rank;
    private String teamName;
    private Integer totalScore;

    public RankResponseDto(Score score) {
        this.rank = score.getRank();
        this.teamName = score.getProject().getTeamName();
        this.totalScore = score.getTotalScore();
    }
}