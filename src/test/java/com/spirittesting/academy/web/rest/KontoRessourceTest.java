package com.spirittesting.academy.web.rest;

import com.spirittesting.academy.domain.Euro;
import com.spirittesting.academy.domain.Konto;
import com.spirittesting.academy.domain.Kunde;
import com.spirittesting.academy.domain.Zahlung;
import com.spirittesting.academy.service.KontoService;
import com.spirittesting.academy.service.KundeService;
import com.spirittesting.academy.service.ZahlungsService;
import com.spirittesting.academy.web.rest.dto.KontoDTO;
import com.spirittesting.academy.web.rest.dto.KontoDetailsDTO;
import com.spirittesting.academy.web.rest.dto.KundeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class KontoRessourceTest {

    KontoRessource kontoRessource;
    KontoService kontoService;
    ZahlungsService zahlungsService;

    List<Konto> konten = Arrays.asList(
            new Konto("1", new Kunde("1", "Hannes")),
            new Konto("2", new Kunde("1", "Hannes")),
            new Konto("3", new Kunde("2", "Werner"))
    );
    List<Zahlung> zahlungen1 = Arrays.asList();
    List<Zahlung> zahlungen2 = Arrays.asList(
            new Zahlung(konten.get(1), new Konto("4", new Kunde("3", "Egon")), new Euro(-50, 25))
    );
    List<Zahlung> zahlungen3 = Arrays.asList(
            new Zahlung(konten.get(2), new Konto("4", new Kunde("3", "Egon")), new Euro(-50)),
            new Zahlung(konten.get(2), new Konto("4", new Kunde("3", "Egon")), new Euro(-50)),
            new Zahlung(new Konto("4", new Kunde("3", "Egon")), konten.get(2), new Euro(200))
    );

    @BeforeEach
    void prepareMocks(@Mock KontoService kontoService, @Mock KundeService kundeService, @Mock ZahlungsService zahlungsService) {
        this.kontoService = kontoService;
        this.zahlungsService = zahlungsService;
        kontoRessource = new KontoRessource(kontoService, kundeService, zahlungsService);

        Mockito.lenient().when(kontoService.getAllKonten()).thenReturn(new HashSet<>(konten));
        Mockito.lenient().when(kontoService.getKonto(eq("1"))).thenReturn(konten.get(0));
        Mockito.lenient().when(kontoService.getKonto(eq("2"))).thenReturn(konten.get(1));
        Mockito.lenient().when(kontoService.getKonto(eq("3"))).thenReturn(konten.get(2));

        Mockito.lenient().when(kontoService.getBetrag(eq(konten.get(0)))).thenReturn(Euro.ZERO);
        Mockito.lenient().when(kontoService.getBetrag(eq(konten.get(1)))).thenReturn(new Euro(-50, 25));
        Mockito.lenient().when(kontoService.getBetrag(eq(konten.get(2)))).thenReturn(new Euro(100));

        Mockito.lenient().when(zahlungsService.getZahlungen(eq("1"))).thenReturn(zahlungen1);
        Mockito.lenient().when(zahlungsService.getZahlungen(eq("2"))).thenReturn(zahlungen2);
        Mockito.lenient().when(zahlungsService.getZahlungen(eq("3"))).thenReturn(zahlungen3);

        Mockito.lenient().when(kundeService.findByKundennummer(eq("1"))).thenReturn(new Kunde("1", "Hannes"));
        Mockito.lenient().when(kontoService.addKonto(any())).thenReturn(new Konto("4", new Kunde("1", "Hannes")));
    }

    @Test
    void getKonten() {
        final List<KontoDTO> konten = kontoRessource.getKonten();
        System.out.println(konten);
        assertEquals(3, konten.size());
        assertEquals("1", konten.get(0).getKontonummer());
        assertEquals(Euro.ZERO, konten.get(0).getBetrag());
        assertEquals("2", konten.get(1).getKontonummer());
        assertEquals(new Euro(-50, 25), konten.get(1).getBetrag());
        assertEquals("3", konten.get(2).getKontonummer());
        assertEquals(new Euro(100), konten.get(2).getBetrag());
    }

    @Test
    void getKonto() {
        final KontoDetailsDTO dto = kontoRessource.getKonto("2");
        assertEquals("2", dto.getKontonummer());
        assertEquals(Euro.ZERO, dto.getKreditrahmen());
        assertEquals(new Euro(-50, 25), dto.getBetrag());
        assertEquals(1, dto.getZahlungen().size());
    }

    @Test
    void addKonto() {
        kontoRessource.addKonto(new KundeDTO("1", "Hannes"));
        Mockito.verify(kontoService).addKonto(new Kunde("1", "Hannes"));
    }

    @Test
    void addZahlung() {
        kontoRessource.addZahlung("1", "2", new Euro(100));
        Mockito.verify(zahlungsService).addZahlung("1", "2", new Euro(100));
        Mockito.verifyNoMoreInteractions(zahlungsService);
    }
}
