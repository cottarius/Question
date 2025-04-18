package ru.cotarius.question.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.cotarius.question.entity.Question;
import ru.cotarius.question.service.QuestionService;

import java.util.List;
import java.util.Random;

/**
 * Контроллер для работы с вопросами.
 * Обрабатывает запросы, связанные с отображением вопросов по различным категориям,
 * поиском вопросов и отображением главной страницы.
 *
 * @author olegprokopenko
 */
@Controller
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final Random random = new Random();

    /**
     * Отображает вопросы по Spring Framework.
     *
     * @param currentIndex текущий индекс вопроса для отображения
     * @param model модель для передачи данных в представление
     * @return имя представления для отображения вопросов
     */
    @GetMapping("/spring/{currentIndex}")
    public String getSpringQuestions(@PathVariable int currentIndex, Model model) {
        List<Question> questions = questionService.getSpringQuestions();
        model.addAttribute("questions", questions);
        model.addAttribute("currentIndex", currentIndex);
        return "questions";
    }

    /**
     * Отображает вопросы по Hibernate.
     *
     * @param currentIndex текущий индекс вопроса для отображения
     * @param model модель для передачи данных в представление
     * @return имя представления для отображения вопросов
     */
    @GetMapping("/hibernate/{currentIndex}")
    public String getHibernateQuestions(@PathVariable int currentIndex, Model model) {
        List<Question> questions = questionService.getHibernateQuestions();
        model.addAttribute("questions", questions);
        model.addAttribute("currentIndex", currentIndex);
        return "questions";
    }

    /**
     * Отображает вопросы по SQL.
     *
     * @param currentIndex текущий индекс вопроса для отображения
     * @param model модель для передачи данных в представление
     * @return имя представления для отображения вопросов
     */
    @GetMapping("/sql/{currentIndex}")
    public String getSqlQuestion(@PathVariable int currentIndex, Model model) {
        List<Question> questions = questionService.getSqlQuestions();
        model.addAttribute("questions", questions);
        model.addAttribute("currentIndex", currentIndex);
        return "questions";
    }

    /**
     * Отображает все вопросы в случайном порядке.
     *
     * @param currentIndex текущий индекс вопроса для отображения
     * @param model модель для передачи данных в представление
     * @return имя представления для отображения всех вопросов
     */
    @GetMapping("/all/{currentIndex}")
    public String getAllQuestions(@PathVariable int currentIndex, Model model){
        List<Question> questions = questionService.findAll();
        int randomIndex = random.nextInt(0, questions.size());
        model.addAttribute("currentIndex", currentIndex);
        model.addAttribute("random", randomIndex);
        model.addAttribute("questions", questions);
        return "all";
    }

    /**
     * Отображает базовые вопросы в случайном порядке.
     *
     * @param currentIndex текущий индекс вопроса для отображения
     * @param model модель для передачи данных в представление
     * @return имя представления для отображения базовых вопросов
     */
    @GetMapping("/primary/{currentIndex}")
    public String getPrimaryQuestions(@PathVariable int currentIndex, Model model) {
        List<Question> questions = questionService.getPrimaryQuestions();
        int randomIndex = random.nextInt(questions.size());
        model.addAttribute("currentIndex", currentIndex);
        model.addAttribute("random", randomIndex);
        model.addAttribute("questions", questions);
        return "primary_questions";
    }

    /**
     * Отображает вопросы по Core Java (часть 1).
     *
     * @param currentIndex текущий индекс вопроса для отображения
     * @param model модель для передачи данных в представление
     * @return имя представления для отображения вопросов
     */
    @GetMapping("/core1/{currentIndex}")
    public String getCore1Questions(@PathVariable int currentIndex, Model model) {
        List<Question> questions = questionService.getCore1Questions();
        model.addAttribute("questions", questions);
        model.addAttribute("currentIndex", currentIndex);
        return "questions";
    }

    /**
     * Отображает вопросы по Core Java (часть 2).
     *
     * @param currentIndex текущий индекс вопроса для отображения
     * @param model модель для передачи данных в представление
     * @return имя представления для отображения вопросов
     */
    @GetMapping("/core2/{currentIndex}")
    public String getCore2Questions(@PathVariable int currentIndex, Model model) {
        List<Question> questions = questionService.getCore2Questions();
        model.addAttribute("questions", questions);
        model.addAttribute("currentIndex", currentIndex);
        return "questions";
    }

    /**
     * Отображает вопросы по Core Java (часть 3).
     *
     * @param currentIndex текущий индекс вопроса для отображения
     * @param model модель для передачи данных в представление
     * @return имя представления для отображения вопросов
     */
    @GetMapping("/core3/{currentIndex}")
    public String getCore3Questions(@PathVariable int currentIndex, Model model) {
        List<Question> questions = questionService.getCore3Questions();
        model.addAttribute("questions", questions);
        model.addAttribute("currentIndex", currentIndex);
        return "questions";
    }

    /**
     * Отображает вопросы по паттернам проектирования.
     *
     * @param currentIndex текущий индекс вопроса для отображения
     * @param model модель для передачи данных в представление
     * @return имя представления для отображения вопросов
     */
    @GetMapping("/patterns/{currentIndex}")
    public String getPatternsQuestions(@PathVariable int currentIndex, Model model){
        List<Question> questions = questionService.getPatternsQuestions();
        model.addAttribute("questions", questions);
        model.addAttribute("currentIndex", currentIndex);
        return "questions";
    }

    /**
     * Осуществляет поиск вопросов по ключевому слову.
     *
     * @param query поисковый запрос
     * @param currentIndex текущий индекс найденного вопроса
     * @param model модель для передачи данных в представление
     * @return имя представления для отображения результатов поиска
     */
    @PostMapping("/search")
    public String searchQuestions(@RequestParam String query,
                                  @RequestParam(required = false, defaultValue = "0") int currentIndex,
                                  Model model) {
        List<Question> questions = questionService.findAll();
        Question foundQuestion = null;
        String message = "No more results found";

        for (int i = currentIndex; i < questions.size(); i++) {
            Question question = questions.get(i);
            if (question.getQuestion().toLowerCase().contains(query.toLowerCase())) {
                foundQuestion = question;
                currentIndex = i + 1;
                break;
            }
        }

        if (foundQuestion != null) {
            model.addAttribute("question", foundQuestion);
            model.addAttribute("query", query);
            model.addAttribute("currentIndex", currentIndex);
        } else {
            model.addAttribute("message", message);
        }
        return "filtered-question";
    }

    /**
     * Отображает главную страницу приложения.
     *
     * @return имя представления главной страницы
     */
    @GetMapping(value = "/index")
    public String mainPage(){
        return "index";
    }
}
