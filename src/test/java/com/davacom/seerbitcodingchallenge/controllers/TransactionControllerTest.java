package com.davacom.seerbitcodingchallenge.controllers;

import com.davacom.seerbitcodingchallenge.entities.Transaction;
import com.davacom.seerbitcodingchallenge.entities.TransactionStatistics;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAddTransaction() throws Exception {
        // Create a transaction
        Transaction transaction = new Transaction(BigDecimal.valueOf(150.25), Instant.now());

        // Convert transaction to JSON
        String jsonTransaction = objectMapper.writeValueAsString(transaction);

        // Perform POST request
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/transactions/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonTransaction))
                .andExpect(status().isCreated())
                .andReturn();
    }

//    @Test
//    public void testGetTransactionStatistics() throws Exception {
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/transactions/statistics")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andReturn();
//
//        String content = result.getResponse().getContentAsString();
//        TransactionStatistics statistics = objectMapper.readValue(content, TransactionStatistics.class);
//
//        // Validate the fields in TransactionStatistics as BigDecimal values
//        assertTrue(statistics.sum().compareTo(BigDecimal.ZERO) >= 0);
//        assertTrue(statistics.avg().compareTo(BigDecimal.ZERO) >= 0);
//        assertTrue(statistics.max().compareTo(BigDecimal.ZERO) >= 0);
//        assertTrue(statistics.min().compareTo(BigDecimal.ZERO) >= 0);
//        assertTrue(statistics.count() >= 0); // Ensure count is non-negative
//    }
    @Test
    public void testDeleteAllTransactions() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/transactions/delete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}