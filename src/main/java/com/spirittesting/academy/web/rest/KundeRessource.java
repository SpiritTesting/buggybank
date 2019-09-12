package com.spirittesting.academy.web.rest;

import com.spirittesting.academy.domain.Euro;
import com.spirittesting.academy.domain.Konto;
import com.spirittesting.academy.domain.Kunde;
import com.spirittesting.academy.service.KontoService;
import com.spirittesting.academy.service.KundeService;
import com.spirittesting.academy.web.rest.dto.KontoDTO;
import com.spirittesting.academy.web.rest.dto.KundeDTO;
import com.spirittesting.academy.web.rest.dto.KundeDetailsDTO;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/kunde")
@Transactional
public class KundeRessource {

    private KontoService kontoService;
    private KundeService kundeService;

    public KundeRessource(KontoService kontoService, KundeService kundeService) {
        this.kontoService = kontoService;
        this.kundeService = kundeService;
    }

    @GetMapping
    public List<KundeDTO> getKunden() {
        return kundeService.getAllKunden().stream().map(KundeDTO::of).sorted().collect(Collectors.toList());
    }

    @GetMapping("{kundennummer}")
    public KundeDetailsDTO getKunde(@PathVariable String kundennummer) {
        Kunde kunde = kundeService.findByKundennummer(kundennummer);
        SortedSet<String> kontonummern =
                kontoService.getKonten(kundennummer).stream().map(Konto::getKontonummer).collect(Collectors.toCollection(TreeSet::new));
        Euro gesamtsaldo =
                kontonummern.stream().map(kontonummer -> kontoService.getBetrag(kontonummer)).reduce(Euro::add).orElse(Euro.ZERO);
        return new KundeDetailsDTO(kundennummer, kunde.getName(), kontonummern, gesamtsaldo);
    }

    @PostMapping
    public KundeDTO addKunde(@RequestBody KundeDTO kundeDTO) {
        return KundeDTO.of(kundeService.addKunde(kundeDTO.getName()));
    }

}
