package com.mefigenia.quizapp.controller;

import com.mefigenia.quizapp.dao.QuizDao;
import com.mefigenia.quizapp.model.Question;
import com.mefigenia.quizapp.model.QuestionWrapper;
import com.mefigenia.quizapp.model.Quiz;
import com.mefigenia.quizapp.model.Response;
import com.mefigenia.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int numQuestions, @RequestParam String title) {
        return quizService.createQuiz(category, numQuestions, title);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuiz(@PathVariable int id) {
        return quizService.getQuiz(id);
    }

    @GetMapping("submit/{quizId}")
    public ResponseEntity<Integer> getScore (@PathVariable int quizId, @RequestBody List<Response> response) {
        return quizService.getScore(quizId, response);
    }

}
