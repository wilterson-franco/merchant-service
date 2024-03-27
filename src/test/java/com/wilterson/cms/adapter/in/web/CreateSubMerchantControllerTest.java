package com.wilterson.cms.adapter.in.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wilterson.cms.application.port.in.CreateSubMerchantUseCase;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(controllers = CreateSubMerchantController.class)
class CreateSubMerchantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateSubMerchantUseCase createSubMerchantUseCase;

    private static final String ENDPOINT = "/sub-merchants";

    @Test
    void whenValidSubMerchantCommand_thenSubMerchantShouldBeCreated() throws Exception {

        SubMerchantDto subMerchantDto = new SubMerchantDto("Big Shoes", Collections.emptyList());

        MvcResult mvcResult = mockMvc.perform(post(ENDPOINT)
                        .content(asJsonString(subMerchantDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andReturn();

        assertThat(mvcResult).isNotNull();
        assertThat(mvcResult.getResponse().getContentType()).isEqualTo("application/json");
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}