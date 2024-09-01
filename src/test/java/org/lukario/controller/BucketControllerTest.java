package org.lukario.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BucketControllerTest {
    @Autowired
    private BucketController bucketController;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
        assertThat(bucketController).isNotNull();
    }

    @Test
    void givenNoBucketsWhenIGetBucketsThenIExpectNoResults() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/buckets"))
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        assertThat(contentAsString).isEqualTo("[]");
    }

    @Test
    void givenIUploadABucketIExpect201Response() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/bucket")
                        .param("Name", "Test bucket")
                        .param("Payment", String.valueOf(100))
                        .param("Target", String.valueOf(500))
                        .param("PaymentRate", "MONTHLY")
                )
                .andExpect(status().is(201))
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
//        assertThat()
    }
}
