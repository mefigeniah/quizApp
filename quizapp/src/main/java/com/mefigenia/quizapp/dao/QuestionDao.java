package com.mefigenia.quizapp.dao;

import com.mefigenia.quizapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {

    List<Question> findByCategory(String category);

    @Query(value = "SELECT * FROM question Where category=:category ORDER BY random() LIMIT :numQuestions", nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(String category, int numQuestions);
}
