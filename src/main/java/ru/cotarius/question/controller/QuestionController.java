package ru.cotarius.question.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.cotarius.question.entity.Question;
import ru.cotarius.question.entity.Theme;
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
    private int index = 0;

//    @GetMapping("/next-question")
//    public String getNextQuestion() {
//        index++;
//        return "redirect:/primary";
//    }

    @GetMapping("/primary")
    public String getPrimaryQuestions(Model model) {
        List<Question> primaryQuestions = new ArrayList<>();
        for (Question question : questionService.findAll()) {
            if (question.isImpotent()){
                primaryQuestions.add(question);
            }
        }
//        int randomIndex = random.nextInt(primaryQuestions.size());
        if (index >= primaryQuestions.size()) {
            index = 0;
        }
        Question question = primaryQuestions.get(index++);
        model.addAttribute("question", question);
        return "questions";
    }

    @GetMapping("/core")
    public String getCoreQuestions(Model model) {
        List<Question> coreQuestions = new ArrayList<>();
        for (Question question : questionService.findAll()) {
            if (question.getTheme().equals(Theme.CORE1)) {
                coreQuestions.add(question);
            }
        }
        int randomIndex = random.nextInt(coreQuestions.size());
        Question question = coreQuestions.get(randomIndex);
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
        List<Question> patternsQuestions = new ArrayList<>();
        for (Question question : questionService.findAll()) {
            if (question.getTheme().equals(Theme.PATTERNS_ALGORITHMS)) {
                patternsQuestions.add(question);
            }
        }
        int randomNumber = random.nextInt(1, patternsQuestions.size());
        Question question = patternsQuestions.get(randomNumber);
        model.addAttribute("question", question);
        return "questions";
    }

    @GetMapping("/index")
    public String getIndex(Model model){
        return "index";
    }
}
