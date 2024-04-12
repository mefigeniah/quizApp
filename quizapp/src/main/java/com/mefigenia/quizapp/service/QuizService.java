package com.mefigenia.quizapp.service;

import com.mefigenia.quizapp.dao.QuestionDao;
import com.mefigenia.quizapp.dao.QuizDao;
import com.mefigenia.quizapp.model.Question;
import com.mefigenia.quizapp.model.QuestionWrapper;
import com.mefigenia.quizapp.model.Quiz;
import com.mefigenia.quizapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQuestions, String title) {
        List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQuestions);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionList(questions);

        quizDao.save(quiz);

        try {
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuiz(int id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questionFromDB = quiz.get().getQuestionList();
        List<QuestionWrapper> questionForUser = new ArrayList<>();

        for(int i =1; i < questionFromDB.toArray().length; i++) {
            QuestionWrapper questionWrapper = new QuestionWrapper(questionFromDB.get(i).getId(), questionFromDB.get(i).getQuestionTitle(), questionFromDB.get(i).getOption1(), questionFromDB.get(i).getOption2(), questionFromDB.get(i).getOption3(), questionFromDB.get(i).getOption4());
            questionForUser.add(questionWrapper);
        }

        try {
            return new ResponseEntity<>(questionForUser, HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Integer> getScore(int quizId, List<Response> response) {
        Optional<Quiz> quiz = quizDao.findById(quizId);
        List<Question> questions = quiz.get().getQuestionList();
        int rightAnswers = 0;

         for(int i = 0; i < questions.toArray().length; i++)
         {
             if (response.get(i).getResponse().equals(questions.get(i).getRightAnswer()))
                 rightAnswers++;
         }
         return new ResponseEntity<>(rightAnswers, HttpStatus.OK);
    }
}
