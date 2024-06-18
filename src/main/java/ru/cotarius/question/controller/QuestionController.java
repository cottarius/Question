package ru.cotarius.question.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.cotarius.question.entity.Question;
import ru.cotarius.question.service.QuestionService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Controller
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final Random random = new Random();
//    private int index = 32;


//    @GetMapping("/next-question")
//    public String getNextQuestion() {
//        index++;
//        return "redirect:/primary";
//    }

    @GetMapping("/hibernate")
    public String getHibernateQuestion(Model model) {
        Question question = questionService.getQuestionFromHibernateQuestions();
        model.addAttribute("question", question);
        return "questions";
    }

    @GetMapping("/sql")
    public String getSqlQuestion(Model model) {
        Question question = questionService.getQuestionFromSqlQuestions();
        model.addAttribute("question", question);
        return "questions";
    }

    @GetMapping("/primary")
    public String getPrimaryQuestions(Model model) {
        Question question = questionService.getQuestionFromPrimaryQuestions();
        model.addAttribute("question", question);
        return "questions";
    }

    @GetMapping("/core")
    public String getCore1Questions(Model model) {
        Question question = questionService.getQuestionFromCore1Questions();
        model.addAttribute("question", question);
        return "questions";
    }

    @GetMapping("/core2")
    public String getCore2Questions(Model model) {
        Question question = questionService.getQuestionFromCore2Questions();
        model.addAttribute("question", question);
        return "questions";
    }

    @GetMapping("/core3")
    public String getCore3Questions(Model model) {
        Question question = questionService.getQuestionFromCore3Questions();
        model.addAttribute("question", question);
        return "questions";
    }

    @GetMapping("/all")
    public String getRandomQuestion(Model model){
        int randomNumber = random.nextInt(1, questionService.findAll().size());
        Question question = questionService.findById(randomNumber).orElseThrow(NoSuchElementException::new);
        model.addAttribute("question", question);
        return "questions";
    }

    @GetMapping("/patterns")
    public String getPatternsQuestions(Model model){
        Question question = questionService.getQuestionFromPatternsQuestions();
        model.addAttribute("question", question);
        return "questions";
    }

    @GetMapping("/index")
    public String getIndex(Model model){
        return "index";
    }

    @PostMapping("/search")
    public String searchQuestions(@RequestParam() String query, Model model) {
        List<Question> questions = questionService.findAll();
        Question findedQuestion = null;
        String message = "No results found";
        for (Question question : questions) {
            if (question.getQuestion().toLowerCase().contains(query.toLowerCase())) {
                findedQuestion = question;
                break;
            }
        }
        if (findedQuestion != null) {
            model.addAttribute("question", findedQuestion);
        } else {
            model.addAttribute("message", message);
        }
        return "filtered-question";
    }
}
