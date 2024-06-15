//package ru.cotarius.question.controller;
//
//import org.aspectj.lang.annotation.Before;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import ru.cotarius.question.entity.Question;
//import ru.cotarius.question.entity.Theme;
//import ru.cotarius.question.repository.QuestionRepository;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.hamcrest.Matchers.is;
//import static org.junit.jupiter.api.Assertions.*;
////import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//class QuestionControllerTest {
//    private MockMvc mockMvc;
//
//    @Mock
//    private QuestionController questionController;
//
//    @InjectMocks
//    private QuestionRepository questionRepository;
//
//    Question question1 = new Question(1L, "Вопрос 1", "Ответ 1", Theme.CORE1, true);
//    Question question2 = new Question(1L, "Вопрос 2", "Ответ 2", Theme.CORE1, false);
//    Question question3 = new Question(1L, "Вопрос 3", "Ответ 3", Theme.SPRING, true);
//    Question question4 = new Question(1L, "Вопрос 4", "Ответ 4", Theme.SPRING, false);
//    Question question5 = new Question(1L, "Вопрос 5", "Ответ 5", Theme.HIBERNATE_JDBC, true);
//    Question question6 = new Question(1L, "Вопрос 6", "Ответ 6", Theme.HIBERNATE_JDBC, false);
//
////    @BeforeEach
////    public void setUp() {
////        MockitoAnnotations.initMocks(this);
////        this.mockMvc = MockMvcBuilders.standaloneSetup(questionController).build();
////    }
//
//    @Test
//    void getAllQuestionsShouldReturnAllQuestions() throws Exception {
//        MockitoAnnotations.initMocks(this);
//        this.mockMvc = MockMvcBuilders.standaloneSetup(questionController).build();
//        List<Question> questions = new ArrayList<>(Arrays.asList(question1, question2, question3, question4, question5, question6));
//
//        Mockito.when(questionRepository.findAll()).thenReturn(questions);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/questions")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)));
//
//    }
//
//    @Test
//    void getPrimaryQuestions() {
//    }
//
//    @Test
//    void getCoreQuestions() {
//    }
//
//    @Test
//    void getCore2Questions() {
//    }
//
//    @Test
//    void getRandomQuestion() {
//    }
//
//    @Test
//    void getPatternsQuestions() {
//    }
//
//    @Test
//    void getIndex() {
//    }
//}