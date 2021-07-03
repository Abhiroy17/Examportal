package com.exam.controller;

import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;
import com.exam.service.QuestionService;
import com.exam.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService service;

    @Autowired
    private QuizService quizService;

    //add question
    @PostMapping("/")
    public ResponseEntity<Question> add(@RequestBody Question question) {
        return ResponseEntity.ok(this.service.addQuestion(question));
    }

    //update the question
    @PutMapping("/")
    public ResponseEntity<Question> update(@RequestBody Question question) {
        return ResponseEntity.ok(this.service.updateQuestion(question));
    }

    //get all question of any quid
    @GetMapping("/quiz/{qid}")
    public ResponseEntity<?> getQuestionsOfQuiz(@PathVariable("qid") Long qid) {
//        Quiz quiz = new Quiz();
//        quiz.setqId(qid);
//        Set<Question> questionsOfQuiz = this.service.getQuestionsOfQuiz(quiz);
//        return ResponseEntity.ok(questionsOfQuiz);

        Quiz quiz = this.quizService.getQuiz(qid);
        Set<Question> questions = quiz.getQuestions();
        List list = new ArrayList(questions);
        if (list.size() > Integer.parseInt(quiz.getNumberOfQuestions())) {
            list = list.subList(0, Integer.parseInt(quiz.getNumberOfQuestions() + 1));
        }
        Collections.shuffle(list);
        return ResponseEntity.ok(list);


    }


    @GetMapping("/quiz/all/{qid}")
    public ResponseEntity<?> getQuestionsOfQuizAdmin(@PathVariable("qid") Long qid) {
        Quiz quiz = new Quiz();
        quiz.setqId(qid);
        Set<Question> questionsOfQuiz = this.service.getQuestionsOfQuiz(quiz);
        return ResponseEntity.ok(questionsOfQuiz);

//        return ResponseEntity.ok(list);


    }


    //get single question
    @GetMapping("/{quesId}")
    public Question get(@PathVariable("quesId") Long quesId) {
        return this.service.getQuestion(quesId);
    }

    //delete question
    @DeleteMapping("/{quesId}")
    public void delete(@PathVariable("quesId") Long quesId) {
        this.service.deleteQuestion(quesId);
    }

}
