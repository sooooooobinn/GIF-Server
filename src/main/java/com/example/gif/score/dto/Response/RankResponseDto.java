package com.example.gif.score.dto.Response;

import com.example.gif.score.entity.Score;
import lombok.Getter;

@Getter
public class RankResponseDto {
    private String teamName;
    private Integer totalScore;
    private int rank;

    public RankResponseDto(Score score) {
        this.teamName = score.getTeamName().getProjectName();
        this.totalScore = score.getTotalScore();
        this.rank = score.getRank();
    }
}
