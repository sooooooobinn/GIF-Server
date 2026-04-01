package com.example.gif.score.dto.Response;

import com.example.gif.score.entity.Score;
import lombok.Getter;

@Getter
public class RankResponseDto {
    private int rank;
    private String teamName;
    private Integer totalScore;

    public RankResponseDto(String teamName, Integer totalScore) {
        this.teamName = teamName;
        this.totalScore = totalScore;
    }

    public void assignRank(int rank) {
        this.rank = rank;
    }
}