package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class DemoApplicationTests {

    @Test
    void contextLoads() {
        // Verifies the Spring application context starts successfully
    }

    @Test
    void mainMethodRuns() {
        // Verifies the main method executes without throwing
        DemoApplication.main(new String[]{});
    }

}