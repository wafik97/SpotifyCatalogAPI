//package com.example.catalog;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class HelloControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void testHome() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/"))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("Hello from API! There is no UI here..."));
//    }
//}
