package ru.cotarius.question.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.cotarius.question.entity.Question;
import ru.cotarius.question.entity.Theme;
import ru.cotarius.question.service.QuestionService;

import java.util.List;
import java.util.Random;

@Controller
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final Random random = new Random();

    private void checkIndex(List<Question> questions) {
        if (questionService.getIndex() >= questions.size() || questionService.getIndex() < 0) {
            questionService.setIndex(0);
        }
    }

    @GetMapping("/spring/{currentIndex}")
    public String getSpringQuestions(@PathVariable int currentIndex, Model model) {
        List<Question> questions = questionService.getQuestionsFromTheme(Theme.SPRING);
        checkIndex(questions);
        model.addAttribute("questions", questions);
        model.addAttribute("currentIndex", currentIndex);
        return "questions";
    }

    @GetMapping("/hibernate/{currentIndex}")
    public String getHibernateQuestions(@PathVariable int currentIndex, Model model) {
        List<Question> questions = questionService.getQuestionsFromTheme(Theme.HIBERNATE_JDBC);
        checkIndex(questions);
        model.addAttribute("questions", questions);
        model.addAttribute("currentIndex", currentIndex);
        return "questions";
    }

    @GetMapping("/sql/{currentIndex}")
    public String getSqlQuestion(@PathVariable int currentIndex, Model model) {
        List<Question> questions = questionService.getQuestionsFromTheme(Theme.SQL_DATABASE);
        checkIndex(questions);
        model.addAttribute("questions", questions);
        model.addAttribute("currentIndex", currentIndex);
        return "questions";
    }

    @GetMapping("/primary/{currentIndex}")
    public String getPrimaryQuestions(@PathVariable int currentIndex, Model model) {
        List<Question> questions = questionService.getPrimaryQuestions();
        checkIndex(questions);
        model.addAttribute("questions", questions);
        model.addAttribute("currentIndex", currentIndex);
        return "questions";
    }

    @GetMapping("/core1/{currentIndex}")
    public String getCore1Questions(@PathVariable int currentIndex, Model model) {
        List<Question> questions = questionService.getQuestionsFromTheme(Theme.CORE1);
        checkIndex(questions);
        model.addAttribute("questions", questions);
        model.addAttribute("currentIndex", currentIndex);
        return "questions";
    }

    @GetMapping("/core2/{currentIndex}")
    public String getCore2Questions(@PathVariable int currentIndex, Model model) {
        List<Question> questions = questionService.getQuestionsFromTheme(Theme.CORE2_COLLECTIONS);
        checkIndex(questions);
        model.addAttribute("questions", questions);
        model.addAttribute("currentIndex", currentIndex);
        return "questions";
    }

    @GetMapping("/core3/{currentIndex}")
    public String getCore3Questions(@PathVariable int currentIndex, Model model) {
        List<Question> questions = questionService.getQuestionsFromTheme(Theme.CORE3_MULTITHREADING);
        checkIndex(questions);
        model.addAttribute("questions", questions);
        model.addAttribute("currentIndex", currentIndex);
        return "questions";
    }

    @GetMapping("/all/{currentIndex}")
    public String getAllQuestions(@PathVariable int currentIndex, Model model){
        List<Question> questions = questionService.findAll();
        int randomIndex = random.nextInt(0, questions.size());
        checkIndex(questions);
        model.addAttribute("random", randomIndex);
        model.addAttribute("questions", questions);
        return "all";
    }

    @GetMapping("/patterns/{currentIndex}")
    public String getPatternsQuestions(@PathVariable int currentIndex, Model model){
        List<Question> questions = questionService.getQuestionsFromTheme(Theme.PATTERNS_ALGORITHMS);
        checkIndex(questions);
        model.addAttribute("questions", questions);
        model.addAttribute("currentIndex", currentIndex);
        return "questions";
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
            model.addAttribute("currentIndex", questionService.getIndex());
        } else {
            model.addAttribute("message", message);
            model.addAttribute("currentIndex", questionService.getIndex());
        }
        return "filtered-question";
    }

    @GetMapping(value = {"/", "/index"})
    public String getIndex(Model model){
        model.addAttribute("currentIndex", questionService.getIndex());
        return "index";
    }
}
