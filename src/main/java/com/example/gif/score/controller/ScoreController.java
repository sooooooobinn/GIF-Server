package com.example.gif.score.controller;

import com.example.gif.score.repository.ScoreRepository;
import com.example.gif.score.service.ScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/score")
public class ScoreController {
    private ScoreService scoreService;
    private ScoreRepository scoreRepository;

    @PostMapping
    public ResponseEntity<?> createScore() {}

    @GetMapping
    public ResponseEntity<?> getScore() {}

    @GetMapping("/rank")
    public ResponseEntity<?> getRank() {}
}
