package com.example.gif.score.controller;

import com.example.gif.score.dto.Request.ScoreRequestDto;
import com.example.gif.score.dto.Response.RankResponseDto;
import com.example.gif.score.dto.Response.ScoreResponseDto;
import com.example.gif.score.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/score")
@RequiredArgsConstructor
public class ScoreController {

    private final ScoreService scoreService;

    @PostMapping
    public ResponseEntity<String> saveScore(@RequestBody ScoreRequestDto dto) {
        scoreService.saveScore(dto);
        return ResponseEntity.ok("점수가 성공적으로 저장되었습니다.");
    }

    @PutMapping
    public ResponseEntity<String> updateScore(@RequestBody ScoreRequestDto dto) {
        scoreService.updateScore(dto);
        return ResponseEntity.ok("점수가 성공적으로 수정되었습니다.");
    }

    @GetMapping
    public ResponseEntity<List<ScoreResponseDto>> getAllScores() {
        return ResponseEntity.ok(scoreService.getAllScores());
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<RankResponseDto>> getFinalRanking() {
        List<RankResponseDto> ranking = scoreService.getFinalRanking();
        return ResponseEntity.ok(ranking);
    }
}
