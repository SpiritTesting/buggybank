package com.spirittesting.academy.web.rest;

import com.spirittesting.academy.domain.Euro;
import com.spirittesting.academy.domain.Konto;
import com.spirittesting.academy.domain.Kunde;
import com.spirittesting.academy.service.KontoService;
import com.spirittesting.academy.service.KundeService;
import com.spirittesting.academy.service.ZahlungsService;
import com.spirittesting.academy.web.rest.dto.KontoDTO;
import com.spirittesting.academy.web.rest.dto.KontoDetailsDTO;
import com.spirittesting.academy.web.rest.dto.KundeDTO;
import com.spirittesting.academy.web.rest.dto.ZahlungDTO;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/konto")
@Transactional
public class KontoRessource {

  private KontoService kontoService;
  private KundeService kundeService;
  private ZahlungsService zahlungsService;

  public KontoRessource(KontoService kontoService, KundeService kundeService, ZahlungsService zahlungsService) {
    this.kontoService = kontoService;
    this.kundeService = kundeService;
    this.zahlungsService = zahlungsService;
  }

  @GetMapping
  public List<KontoDTO> getKonten() {
    return kontoService.getAllKonten().stream().filter(konto -> !konto.getKontonummer().equals("000")).map(konto -> new KontoDTO(konto.getKontonummer(),
      kontoService.getBetrag(konto), konto.getName())).sorted().collect(Collectors.toList());
  }

  @GetMapping("{kontonummer}")
  public KontoDetailsDTO getKonto(@PathVariable String kontonummer) {
    Konto konto = kontoService.getKonto(kontonummer);
    return details(konto);
  }

  @PostMapping
  public KontoDTO addKonto(@RequestBody KundeDTO kundeDTO) {
    Kunde kunde = kundeService.findByKundennummer(kundeDTO.getKundennummer());
    Konto konto = kontoService.addKonto(kunde);
    return new KontoDTO(konto.getKontonummer(), Euro.ZERO, "");
  }

  @PostMapping("{quelle}/{ziel}")
  public void addZahlung(@PathVariable String quelle, @PathVariable String ziel, @RequestBody ZahlungDTO zahlung) {
    zahlungsService.addZahlung(quelle, ziel, zahlung.getBetrag(), zahlung.getZweck());
  }

  @PutMapping("{kontonummer}")
  public KontoDetailsDTO setName(@PathVariable String kontonummer, @RequestBody KontoDTO dto) {
    return details(kontoService.setName(kontonummer, dto.getName()));
  }

  private KontoDetailsDTO details(Konto konto) {
    SortedSet<ZahlungDTO> zahlungen =
      zahlungsService.getZahlungen(konto.getKontonummer()).stream()
        .map(z -> new ZahlungDTO(
          z.getDatum(),
          z.getQuelle().getKontonummer(),
          z.getZiel().getKontonummer(),
          z.getBetrag(),
          z.getZweck()))
        .collect(Collectors.toCollection(TreeSet::new));
    return new KontoDetailsDTO(konto.getKontonummer(), konto.getKreditrahmen(), kontoService.getBetrag(konto),
      zahlungen, konto.getName());
  }

}
