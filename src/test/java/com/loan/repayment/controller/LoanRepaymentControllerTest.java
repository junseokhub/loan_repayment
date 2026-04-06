package com.loan.repayment.controller;

import com.loan.repayment.domain.RepaymentType;
import com.loan.repayment.domain.dto.CalculateRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LoanRepaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void calculatePlanSuccess() throws Exception {
        CalculateRequestDto request = new CalculateRequestDto(
                new BigDecimal("1000000"),
                12,
                new BigDecimal("0.05"),
                RepaymentType.EQUAL_INSTALLMENT
        );

        mockMvc.perform(post("/api/v1/loan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(12))
                .andExpect(jsonPath("$[0].installmentNo").exists())
                .andExpect(jsonPath("$[0].principal").exists())
                .andExpect(jsonPath("$[0].interest").exists());
    }
}
