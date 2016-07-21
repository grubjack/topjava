package ru.javawebinar.topjava.web;

import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by user on 21.07.2016.
 */
public class ResourceControllerTest extends AbstractControllerTest {

    private static final String CSS_URL = "/resources/css/style.css";

    @Test
    public void testCss() throws Exception {
        mockMvc.perform(get(CSS_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/css"));
    }
}
