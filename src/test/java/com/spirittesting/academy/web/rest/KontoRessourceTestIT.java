package com.spirittesting.academy.web.rest;

import com.spirittesting.academy.domain.Euro;
import com.spirittesting.academy.service.KontoService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Sql({"/Kunden.sql", "/Maximalzahlung.sql"})
@Rollback(false)
class KontoRessourceTestIT {

    @Autowired
    MockMvc mvc;

    @Autowired
    KontoService kontoService;

    @Test
    void getKonten() throws Exception {
        mvc.perform(get("/api/konto").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].kontonummer", is("12340001")))
                .andExpect(jsonPath("$[1].kontonummer", is("12340002")))
                .andExpect(jsonPath("$[0].betrag", is("EUR -200.00")))
                .andExpect(jsonPath("$[1].betrag", is("EUR 200.00")));
    }

    @Test
    void getKonto() throws Exception {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        mvc.perform(get("/api/konto/12340001").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.kontonummer", is("12340001")))
                .andExpect(jsonPath("$.betrag", is("EUR -200.00")))
                .andExpect(jsonPath("$.kreditrahmen", is("EUR 200.00")))
                .andExpect(jsonPath("$.zahlungen", hasSize(1)))
                .andExpect(jsonPath("$.zahlungen[0].quelle", is("12340001")))
                .andExpect(jsonPath("$.zahlungen[0].ziel", is("12340002")))
                .andExpect(jsonPath("$.zahlungen[0].betrag", is("EUR 200.00")))
                .andExpect(jsonPath("$.zahlungen[0].datum", is(date)));
    }

    @Test void addKonto() throws Exception {
        mvc.perform(post("/api/konto").contentType(MediaType.APPLICATION_JSON).content("{\"kundennummer\":\"2" +
          "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.kontonummer", is("12340001")))
                .andExpect(jsonPath("$.betrag", is("EUR 0.00")));
    }

    @Test
    void addZahlung() throws Exception {
        mvc.perform(post("/api/konto/12340002/12340001").contentType(MediaType.APPLICATION_JSON).content("{\"betrag" +
          "\":\"EUR 200.00\"}"))
                .andExpect(status().isOk());
        assertEquals(Euro.ZERO, kontoService.getBetrag("12340001"));
        assertEquals(Euro.ZERO, kontoService.getBetrag("12340002"));
    }
}
