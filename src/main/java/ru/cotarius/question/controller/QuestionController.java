package ru.cotarius.question.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.cotarius.question.entity.Answer;
import ru.cotarius.question.entity.Question;
import ru.cotarius.question.service.AnswerService;
import ru.cotarius.question.service.QuestionService;

import java.util.NoSuchElementException;
import java.util.Random;

@Controller
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final Random random = new Random();

    @GetMapping("/question")
    public String getRandomQuestion(Model model){
        int randomNumber = random.nextInt(1, questionService.findAll().size());
        Question question = questionService.findById(randomNumber).orElseThrow(NoSuchElementException::new);
        Answer answer = answerService.findByQuestionId(question.getId()).orElseThrow(NoSuchElementException::new);
        model.addAttribute("question", question);
        model.addAttribute("answer", answer);
        return "question";
    }
}
