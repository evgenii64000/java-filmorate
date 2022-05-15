package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    private Film testFilm = Film.builder()
            .name("TestName")
            .description("testDescription")
            .releaseDate(LocalDate.of(2000, 1, 1))
            .duration(200L)
            .build();

    @Test
    public void createFilmOKTest() throws Exception {
        Film expectedFilm = testFilm;
        expectedFilm.setId(1L);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testFilm));
        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().string(mapper.writeValueAsString(expectedFilm)));
    }

    @Test
    public void createFilmWrongNameTest() throws Exception {
        testFilm.setName("");
        RequestBuilder request = MockMvcRequestBuilders
                .post("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testFilm));
        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    public void createFilmWrongDescriptionTest() throws Exception {
        testFilm.setDescription("");
        RequestBuilder request = MockMvcRequestBuilders
                .post("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testFilm));
        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    public void createFilmWrongReleaseDateTest() throws Exception {
        testFilm.setReleaseDate(null);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testFilm));
        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().is(400));

    }

    @Test
    public void createFilmWrongDurationTest() throws Exception {
        testFilm.setDuration(-200L);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testFilm));
        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    public void getFilmsTest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/films");
        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    public void updateFilmOKTest() throws Exception {
        Film expectedFilm = testFilm;
        expectedFilm.setId(1L);
        expectedFilm.setName("AnotherTestName");
        RequestBuilder request = MockMvcRequestBuilders
                .post("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testFilm));
        testFilm.setName("AnotherTestName");
        RequestBuilder requestUpdate = MockMvcRequestBuilders
                .put("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testFilm));
        this.mockMvc.perform(requestUpdate)
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().string(mapper.writeValueAsString(expectedFilm)));
    }

    @Test
    public void updateFilmWrongNameTest() throws Exception {
        Film expectedFilm = testFilm;
        expectedFilm.setId(1L);
        expectedFilm.setName("AnotherTestName");
        RequestBuilder request = MockMvcRequestBuilders
                .post("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testFilm));
        testFilm.setName("");
        RequestBuilder requestUpdate = MockMvcRequestBuilders
                .put("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testFilm));
        this.mockMvc.perform(requestUpdate)
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    public void updateFilmWrongDescriptionTest() throws Exception {
        Film expectedFilm = testFilm;
        expectedFilm.setId(1L);
        expectedFilm.setName("AnotherTestName");
        RequestBuilder request = MockMvcRequestBuilders
                .post("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testFilm));
        testFilm.setDescription("");
        RequestBuilder requestUpdate = MockMvcRequestBuilders
                .put("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testFilm));
        this.mockMvc.perform(requestUpdate)
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    public void updateFilmWrongReleaseDateTest() throws Exception {
        Film expectedFilm = testFilm;
        expectedFilm.setId(1L);
        expectedFilm.setName("AnotherTestName");
        RequestBuilder request = MockMvcRequestBuilders
                .post("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testFilm));
        testFilm.setReleaseDate(null);
        RequestBuilder requestUpdate = MockMvcRequestBuilders
                .put("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testFilm));
        this.mockMvc.perform(requestUpdate)
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    public void updateFilmWrongDurationTest() throws Exception {
        Film expectedFilm = testFilm;
        expectedFilm.setId(1L);
        expectedFilm.setName("AnotherTestName");
        RequestBuilder request = MockMvcRequestBuilders
                .post("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testFilm));
        testFilm.setDuration(-200L);
        RequestBuilder requestUpdate = MockMvcRequestBuilders
                .put("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testFilm));
        this.mockMvc.perform(requestUpdate)
                .andDo(print())
                .andExpect(status().is(400));
    }
}
