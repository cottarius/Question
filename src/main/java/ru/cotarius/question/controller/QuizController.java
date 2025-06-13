package ru.cotarius.question.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.cotarius.question.service.QuizService;

import java.util.Map;

/**
 * Контроллер для обработки викторины Java Quizzer.
 * Управляет запуском, ответами на вопросы, завершением и переходами между этапами викторины.
 */
@Controller
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    /**
     * Запускает новую викторину, инициализирует сессию и загружает первый вопрос.
     *
     * @param model модель для передачи данных в шаблон
     * @return представление следующего вопроса
     */
    @GetMapping("/quiz/start")
    public String startQuiz(Model model) {
        quizService.initQuizSession();
        return loadNext(model);
    }

    /**
     * Обрабатывает ответ пользователя на текущий вопрос и загружает следующий.
     *
     * @param id     идентификатор вопроса
     * @param answer выбранный пользователем ответ
     * @param model  модель для передачи данных в шаблон
     * @return представление следующего вопроса или результат
     */
    @PostMapping("/quiz/answer")
    public String checkAnswer(@RequestParam Long id,
                              @RequestParam String answer,
                              Model model) {
        quizService.checkAnswerAndUpdate(id, answer);
        return loadNext(model);
    }

    /**
     * Завершает викторину и отображает результаты.
     *
     * @param model модель для передачи финального счёта
     * @return представление с результатами викторины
     */
    @GetMapping("/quiz/end")
    public String endQuiz(Model model) {
        model.addAllAttributes(quizService.getQuizResult());
        return "quiz_result";
    }

    /**
     * Перенаправляет пользователя к старту викторины.
     *
     * @return редирект на /quiz/start
     */
    @GetMapping("/quiz")
    public String redirectToStart() {
        return "redirect:/quiz/start";
    }

    /**
     * Загружает следующий вопрос из сессии или завершает викторину.
     *
     * @param model модель для передачи вопроса и вариантов ответа
     * @return представление следующего вопроса или редирект на /quiz/end
     */
    private String loadNext(Model model) {
        Map<String, Object> data = quizService.getNextQuestion();
        if (data.containsKey("redirect")) {
            return "redirect:" + data.get("redirect");
        }
        model.addAllAttributes(data);
        return "quiz";
    }
}