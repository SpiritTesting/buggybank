package com.spirittesting.academy.service;

import com.spirittesting.academy.domain.Euro;
import com.spirittesting.academy.domain.Konto;
import com.spirittesting.academy.domain.Kunde;
import com.spirittesting.academy.domain.Zahlung;
import com.spirittesting.academy.exceptions.KontoNotFoundException;
import com.spirittesting.academy.repository.KontoRepository;
import com.spirittesting.academy.repository.ZahlungRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class KontoService {

  private KontonummerService kontonummerService;
  private KontoRepository kontoRepository;
  private ZahlungRepository zahlungRepository;

  public KontoService(KontonummerService kontonummerService, KontoRepository kontoRepository, ZahlungRepository zahlungRepository) {
    this.kontonummerService = kontonummerService;
    this.kontoRepository = kontoRepository;
    this.zahlungRepository = zahlungRepository;
  }

  public Konto addKonto(Kunde kunde) {
    if (kunde == null) throw new IllegalArgumentException("Kunde darf nicht NULL sein");
    Konto konto = new Konto(kontonummerService.next(), kunde);
    konto = kontoRepository.save(konto);
    return konto;
  }

  Konto addKonto(String kontonummer) {
    Konto konto = new Konto(kontonummer, new Kunde("000", "Buggybank"));
    konto = kontoRepository.save(konto);
    return konto;
  }

  public Konto getKonto(String kontonummer) throws KontoNotFoundException {
    return kontoRepository.findById(kontonummer).orElseThrow(() -> new KontoNotFoundException(kontonummer));
  }

  public Set<Konto> getKonten(String kundennummer) {
    return kontoRepository.findAllByKunde_Kundennummer(kundennummer);
  }

  @Transactional
  public void setKreditrahmen(String kontonummer, Euro kreditrahmen) {
    Konto konto = getKonto(kontonummer);
    konto.setKreditrahmen(kreditrahmen);
    kontoRepository.save(konto);
  }

  @Transactional
  public Euro getBetrag(Konto konto) {
    return getBetrag(konto.getKontonummer());
  }

  @Transactional
  public Euro getBetrag(String kontonummer) {
    final Euro betrag =
      zahlungRepository.findAllByZiel_Kontonummer(kontonummer).stream().map(Zahlung::getBetrag).reduce(Euro::add).orElse(Euro.ZERO).subtract(zahlungRepository.findAllByQuelle_Kontonummer(kontonummer).stream().map(Zahlung::getBetrag).reduce(Euro::add).orElse(Euro.ZERO));
    return betrag;
  }

  @Transactional
  public Set<Konto> getAllKonten() {
    return new HashSet<>(kontoRepository.findAll());
  }

  @Transactional
  public Konto setName(String kontonummer, String name) {
    final Konto konto = getKonto(kontonummer);
    konto.setName(name);
    return kontoRepository.save(konto);
  }

}
