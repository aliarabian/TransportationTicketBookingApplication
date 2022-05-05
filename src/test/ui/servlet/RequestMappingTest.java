package ui.servlet;

import org.junit.jupiter.api.Test;
import ui.servlet.frontcontroller.RequestMapping;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RequestMappingTest {

    @Test
    void matches() {
        RequestMapping requestMapping = new RequestMapping("GET", "/app/home/{username}");
        assertTrue(requestMapping.matches("get", "/app/home/ali"));
    }
}