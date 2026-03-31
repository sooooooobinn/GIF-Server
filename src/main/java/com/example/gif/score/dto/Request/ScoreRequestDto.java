package com.example.gif.score.dto.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScoreRequestDto {
    private Long projectId;
    private Integer technicalScore;
    private Integer socialValueScore;
    private Integer aiUtilityScore;
    private Integer presentationScore;
}
