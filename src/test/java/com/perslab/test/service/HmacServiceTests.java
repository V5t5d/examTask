package com.perslab.test.service;

import com.perslab.task.TaskApplication;
import com.perslab.task.service.HmacService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TaskApplication.class)
@TestPropertySource(properties = "app.security.token=test")
public class HmacServiceTests {

    private final String UNHASHED_VALUE = "123";
    private final String HASHED_VALUE = "FKJxXTWyVAcMshXrtwE+2YjTgpRcKzbdtZkSFO95x5Y=";

    @Test
    public void validHashResult_returnsCorrectHash() throws Exception {
        var res = HmacService.generateHMACSignature(UNHASHED_VALUE);
        assertEquals(res, HASHED_VALUE);
    }
}
