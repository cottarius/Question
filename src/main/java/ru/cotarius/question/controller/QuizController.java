package ru.cotarius.question.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.cotarius.question.service.QuizService;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/quiz/start")
    public String startQuiz(Model model) {
        quizService.initQuizSession();
        return loadNext(model);
    }

    @PostMapping("/quiz/answer")
    public String checkAnswer(@RequestParam Long id,
                              @RequestParam String answer,
                              Model model) {
        quizService.checkAnswerAndUpdate(id, answer);
        return loadNext(model);
    }

    @GetMapping("/quiz/end")
    public String endQuiz(Model model) {
        model.addAllAttributes(quizService.getQuizResult());
        return "quiz_result";
    }

    @GetMapping("/quiz")
    public String redirectToStart() {
        return "redirect:/quiz/start";
    }

    private String loadNext(Model model) {
        Map<String, Object> data = quizService.getNextQuestion();
        if (data.containsKey("redirect")) {
            return "redirect:" + data.get("redirect");
        }
        model.addAllAttributes(data);
        return "quiz";
    }
}