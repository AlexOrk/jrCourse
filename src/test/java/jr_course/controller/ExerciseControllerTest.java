//package jr_course.controller;
//
//import jr_course.entity.Exercise;
//import jr_course.service.ExerciseService;
//import jr_course.service.GrammarService;
//import jr_course.service.mq.Producer;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(ExerciseController.class)
//public class ExerciseControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ExerciseService exerciseService;
//    @MockBean
//    private GrammarService grammarService;
//    @MockBean
//    private Producer producer;
//
//    private Exercise exercise;
//
//    @Before
//    public void setUp() throws Exception {
//        exercise = new Exercise();
//    }
//
//    @Test
//    public void findExercises_basic() throws Exception {
//        RequestBuilder request = MockMvcRequestBuilders
//                                .get("/exercises")
//                                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML);
//        MvcResult mvcResult = mockMvc.perform(request)
//                .andExpect(status().isOk())
//                .andExpect(content().json())
//                .andReturn();
//
//        System.out.println("!!!!!!!!");
//        System.out.println(mvcResult.getResponse().getContentAsString());
//    }
//}
