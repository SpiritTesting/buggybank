package com.spirittesting.academy.web.rest;

import com.spirittesting.academy.BuggyBankApp;
import com.spirittesting.academy.domain.Euro;
import com.spirittesting.academy.domain.Konto;
import com.spirittesting.academy.domain.Kunde;
import com.spirittesting.academy.service.KontoService;
import com.spirittesting.academy.service.KundeService;
import com.spirittesting.academy.service.ZahlungsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Sql({"/Kunden.sql", "/Maximalzahlung.sql"})
@Rollback(false)
class KundeRessourceTestIT {

    @Autowired
    MockMvc mvc;

    @Test
    void getKunden() throws Exception {
        mvc.perform(get("/api/kunde").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].kundennummer", is("000")))
                .andExpect(jsonPath("$[1].kundennummer", is("1")))
                .andExpect(jsonPath("$[2].kundennummer", is("2")))
                .andExpect(jsonPath("$[0].name", is("ZINS")))
                .andExpect(jsonPath("$[1].name", is("Hannes")))
                .andExpect(jsonPath("$[2].name", is("Werner")));
    }

    @Test
    void getKunde() throws Exception {
        mvc.perform(get("/api/kunde/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.kundennummer", is("1")))
                .andExpect(jsonPath("$.name", is("Hannes")))
                .andExpect(jsonPath("$.konten", hasSize(1)))
                .andExpect(jsonPath("$.konten[0]", is("1")))
                .andExpect(jsonPath("$.saldo", is("EUR -200.00")));
    }

    @Test void addKunde() throws Exception {
        mvc.perform(post("/api/kunde").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"Brösel\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.kundennummer", is("KDNR0001")))
                .andExpect(jsonPath("$.name", is("Brösel")));
    }
}
