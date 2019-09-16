package com.spirittesting.academy.web.rest;

import com.spirittesting.academy.domain.Euro;
import com.spirittesting.academy.domain.Konto;
import com.spirittesting.academy.domain.Kunde;
import com.spirittesting.academy.service.KontoService;
import com.spirittesting.academy.service.KundeService;
import com.spirittesting.academy.web.rest.dto.KundeDTO;
import com.spirittesting.academy.web.rest.dto.KundeDetailsDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class KundeRessourceTest {

    KundeRessource kundeRessource;
    KundeService kundeService;

    List<Kunde> kunden = Arrays.asList(
            new Kunde("1", "Hannes"),
            new Kunde("2", "Werner"),
            new Kunde("3", "Egon")
    );

    List<Konto> konten = Arrays.asList(
            new Konto("1", kunden.get(0)),
            new Konto("2", kunden.get(0))
    );

    @BeforeEach
    void prepareMocks(@Mock KontoService kontoService, @Mock KundeService kundeService) {
        this.kundeService = kundeService;
        kundeRessource = new KundeRessource(kontoService, kundeService);

        Mockito.lenient().when(kundeService.getAllKunden()).thenReturn(kunden);
        Mockito.lenient().when(kundeService.findByKundennummer(eq("1"))).thenReturn(kunden.get(0));
        Mockito.lenient().when(kontoService.getKonten(eq("1"))).thenReturn(new HashSet<>(konten));

        Mockito.lenient().when(kontoService.getBetrag(eq("1"))).thenReturn(new Euro(10, 50));
        Mockito.lenient().when(kontoService.getBetrag(eq("2"))).thenReturn(new Euro(-50, 25));
      Mockito.lenient().when(kontoService.getBetrag(eq(konten.get(0)))).thenReturn(new Euro(10, 50));
      Mockito.lenient().when(kontoService.getBetrag(eq(konten.get(1)))).thenReturn(new Euro(-50, 25));

        Mockito.lenient().when(kundeService.addKunde(any())).thenReturn(new Kunde("4", "Paul"));
    }

    @Test
    void getKunden() {
        final List<KundeDTO> dtos = kundeRessource.getKunden();
        assertEquals(3, dtos.size());

        KundeDTO dto = dtos.get(0);
        assertEquals("Hannes", dto.getName());
        assertEquals("1", dto.getKundennummer());
    }

    @Test
    void getKunde() {
        final KundeDetailsDTO dto = kundeRessource.getKunde("1");
        assertEquals("Hannes", dto.getName());
        assertEquals("1", dto.getKundennummer());
        assertEquals(2, dto.getKonten().size());
        assertEquals(new Euro(-39, 75), dto.getSaldo());
    }

    @Test
    void addKunde() {
        final KundeDTO paul = kundeRessource.addKunde(new KundeDTO(null, "Paul"));
        assertEquals("4", paul.getKundennummer());
        assertEquals("Paul", paul.getName());
        Mockito.verify(kundeService).addKunde("Paul");
        Mockito.verifyNoMoreInteractions(kundeService);
    }
}
