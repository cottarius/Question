package ru.cotarius.question.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.cotarius.question.entity.QuizQuestion;
import ru.cotarius.question.repository.QuizQuestionRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class QuizController {

    private final QuizQuestionRepository repository;

    @Autowired
    public QuizController(QuizQuestionRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/quiz/start")
    public String startQuiz(HttpSession session, Model model) {
        List<QuizQuestion> questionPool = new ArrayList<>(repository.findRandom10());
        Collections.shuffle(questionPool);

        session.setAttribute("questionPool", questionPool);
        session.setAttribute("currentIndex", 0);
        session.setAttribute("correctCount", 0);

        return loadNext(session, model);
    }

    @PostMapping("/quiz/answer")
    public String checkAnswer(@RequestParam Long id,
                              @RequestParam String answer,
                              HttpSession session,
                              Model model) {
        QuizQuestion question = repository.findById(id).orElseThrow();
        int correctCount = (int) session.getAttribute("correctCount");

        if (question.getCorrectAnswer().equals(answer)) {
            session.setAttribute("correctCount", correctCount + 1);
        }

        return loadNext(session, model);
    }

    @GetMapping("/quiz/end")
    public String endQuiz(HttpSession session, Model model) {
        int correctCount = (int) session.getAttribute("correctCount");
        List<QuizQuestion> questionPool = (List<QuizQuestion>) session.getAttribute("questionPool");

        model.addAttribute("score", correctCount);
        model.addAttribute("total", questionPool.size());
        return "quiz_result";
    }

    @GetMapping("/quiz")
    public String redirectToStart() {
        return "redirect:/quiz/start";
    }

    private String loadNext(HttpSession session, Model model) {
        List<QuizQuestion> questionPool = (List<QuizQuestion>) session.getAttribute("questionPool");
        int currentIndex = (int) session.getAttribute("currentIndex");

        if (currentIndex >= questionPool.size()) {
            return "redirect:/quiz/end";
        }

        QuizQuestion current = questionPool.get(currentIndex);
        session.setAttribute("currentIndex", currentIndex + 1);

        List<String> answers = new ArrayList<>(List.of(
                current.getCorrectAnswer(),
                current.getWrongAnswer(),
                current.getWrongAnswer2(),
                current.getWrongAnswer3()
        ));
        Collections.shuffle(answers);

        model.addAttribute("question", current);
        model.addAttribute("answers", answers);
        return "quiz";
    }
}