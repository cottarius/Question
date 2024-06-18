package ru.cotarius.question.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

//    @GetMapping("/temp")
//    public String temp(Model model) {
//        List<Question> hibernateQuestions = questionService.getHibernateQuestions();
//        checkIndex(hibernateQuestions);
//        model.addAttribute("index", index++);
//        model.addAttribute("hibernateQuestions", hibernateQuestions);
//
//        return "temp";
//    }

    private void checkIndex(List<Question> questions) {
        if (index >= questions.size()) {
            index = 0;
        }
    }

    @GetMapping("/hibernate")
    public String getHibernateQuestions(Model model) {
        List<Question> questions = questionService.getQuestionsFromTheme(Theme.HIBERNATE_JDBC);
        checkIndex(questions);
        model.addAttribute("questions", questions);
        model.addAttribute("index", index++);
        return "questions";
    }

    @GetMapping("/sql")
    public String getSqlQuestion(Model model) {
        List<Question> questions = questionService.getQuestionsFromTheme(Theme.SQL_DATABASE);
        checkIndex(questions);
        model.addAttribute("questions", questions);
        model.addAttribute("index", index++);
        return "questions";
    }

    @GetMapping("/primary")
    public String getPrimaryQuestions(Model model) {
        List<Question> questions = questionService.getPrimaryQuestions();
        checkIndex(questions);
        model.addAttribute("questions", questions);
        model.addAttribute("index", index++);
        return "questions";
    }

    @GetMapping("/core")
    public String getCore1Questions(Model model) {
        List<Question> questions = questionService.getQuestionsFromTheme(Theme.CORE1);
        checkIndex(questions);
        model.addAttribute("questions", questions);
        model.addAttribute("index", index++);
        return "questions";
    }

    @GetMapping("/core2")
    public String getCore2Questions(Model model) {
        List<Question> questions = questionService.getQuestionsFromTheme(Theme.CORE2_COLLECTIONS);
        checkIndex(questions);
        model.addAttribute("questions", questions);
        model.addAttribute("index", index++);
        return "questions";
    }

    @GetMapping("/core3")
    public String getCore3Questions(Model model) {
        List<Question> questions = questionService.getQuestionsFromTheme(Theme.CORE3_MULTITHREADING);
        checkIndex(questions);
        model.addAttribute("questions", questions);
        model.addAttribute("index", index++);
        return "questions";
    }

    @GetMapping("/all")
    public String getRandomQuestion(Model model){
        index = random.nextInt(0, questionService.findAll().size());
        List<Question> questions = questionService.findAll();
        checkIndex(questions);
//        Question question = questionService.findById(randomIndex).orElseThrow(NoSuchElementException::new);
        model.addAttribute("questions", index++);
        return "questions";
    }

    @GetMapping("/patterns")
    public String getPatternsQuestions(Model model){
        List<Question> questions = questionService.getQuestionsFromTheme(Theme.PATTERNS_ALGORITHMS);
        checkIndex(questions);
        model.addAttribute("questions", questions);
        model.addAttribute("index", index++);
        return "questions";
    }

    @PostMapping("/search")
    public String searchQuestions(@RequestParam() String query, Model model) {
        List<Question> questions = questionService.findAll();
        List<Question> filteredQuestions = new ArrayList<>();
        for (Question question : questions) {
            if (question.getQuestion().toLowerCase().contains(query.toLowerCase())) {
//                filteredQuestions.add(question);
                model.addAttribute("question", question);
                break;
            }
        }
        return "questions";
    }

    @GetMapping("/index")
    public String getIndex(Model model){
        return "index";
    }
}
