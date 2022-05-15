package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    private User testUser = User.builder()
            .email("test@mail.ru")
            .login("testLogin")
            .name("TestName")
            .birthday(LocalDate.of(2000, 1, 1))
            .build();

    @Test
    public void createUserOKTest() throws Exception {
        User expectedUser = testUser;
        expectedUser.setId(1L);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testUser));
        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().string(mapper.writeValueAsString(expectedUser)));
    }

    @Test
    public void createUserWrongEmailTest() throws Exception {
        testUser.setEmail("");
        RequestBuilder request = MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testUser));
        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    public void createUserWrongLoginTest() throws Exception {
        testUser.setLogin("");
        RequestBuilder request = MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testUser));
        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    public void createUserWrongBirthdayTest() throws Exception {
        testUser.setBirthday(LocalDate.of(2023, 1, 1));
        RequestBuilder request = MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testUser));
        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    public void createNullUserTest() throws Exception {
        testUser = null;
        RequestBuilder request = MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testUser));
        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    public void getUsersTest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/users");
        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    public void updateUserOKTest() throws Exception {
        User expectedUser = testUser;
        expectedUser.setId(1L);
        expectedUser.setName("AnotherTestName");
        RequestBuilder request = MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testUser));
        testUser.setName("AnotherTestName");
        RequestBuilder requestUpdate = MockMvcRequestBuilders
                .put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testUser));
        this.mockMvc.perform(requestUpdate)
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().string(mapper.writeValueAsString(expectedUser)));
    }

    @Test
    public void updateUserWrongEmailTest() throws Exception {
        User expectedUser = testUser;
        expectedUser.setId(1L);
        expectedUser.setName("AnotherTestName");
        RequestBuilder request = MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testUser));
        testUser.setName("AnotherTestName");
        testUser.setEmail("");
        RequestBuilder requestUpdate = MockMvcRequestBuilders
                .put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testUser));
        this.mockMvc.perform(requestUpdate)
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    public void updateUserWrongLoginTest() throws Exception {
        User expectedUser = testUser;
        expectedUser.setId(1L);
        expectedUser.setName("AnotherTestName");
        RequestBuilder request = MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testUser));
        testUser.setName("AnotherTestName");
        testUser.setLogin("");
        RequestBuilder requestUpdate = MockMvcRequestBuilders
                .put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testUser));
        this.mockMvc.perform(requestUpdate)
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    public void updateUserWrongBirthdayTest() throws Exception {
        User expectedUser = testUser;
        expectedUser.setId(1L);
        expectedUser.setName("AnotherTestName");
        RequestBuilder request = MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testUser));
        testUser.setName("AnotherTestName");
        testUser.setBirthday(null);
        RequestBuilder requestUpdate = MockMvcRequestBuilders
                .put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testUser));
        this.mockMvc.perform(requestUpdate)
                .andDo(print())
                .andExpect(status().is(400));
    }
}
