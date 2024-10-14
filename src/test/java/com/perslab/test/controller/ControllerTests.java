package com.perslab.test.controller;

import com.perslab.task.TaskApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@ContextConfiguration

@SpringBootTest(classes = TaskApplication.class)
@AutoConfigureMockMvc
public class ControllerTests {


    @Autowired
    private MockMvc mockMvc;

    private final String VALID_TOKEN = "token-value";
    private final String WRONG_TOKEN = "wrong-token";
    private final String OPERATION_ID = "123";
    private final String REQUEST_BODY = "{\"name1\":\"value1\",\"name2\":\"value2\"}";
    private final String CORRECT_BASE64_HASH = "vCpg9Q/zCe0WDDHWlkSg127xtq7woP8Z5X8uBnsMhyo=";


    @Test
    public void tokenIsValid_ReturnsOk() throws Exception {
        mockMvc.perform(post("/" + OPERATION_ID).
                        header("Token", VALID_TOKEN).
                        contentType(MediaType.APPLICATION_JSON).
                        content(REQUEST_BODY)).
                andExpect(status().isOk());
    }

    @Test
    public void tokenIsMissing_ReturnsForbidden() throws Exception {
        mockMvc.perform(post("/123"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void tokenIsInvalid_ReturnsForbidden() throws Exception {
        mockMvc.perform(post("/" + OPERATION_ID)
                        .header(HttpHeaders.AUTHORIZATION, "invalidtoken"))
                .andExpect(status().isForbidden());
    }
}
