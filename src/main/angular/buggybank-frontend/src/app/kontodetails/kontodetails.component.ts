import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {MessageService} from 'primeng/api';
import {RestService} from '../rest.service';
import {Kontodetails} from './kontodetails.model';

@Component({
             selector: 'spirit-kontodetails',
             templateUrl: './kontodetails.component.html',
             styleUrls: ['./kontodetails.component.scss']
           })
export class KontodetailsComponent implements OnInit {
  konto: Kontodetails;

  constructor(private route: ActivatedRoute, private restService: RestService, private messageService: MessageService) { }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const kontonummer = params.get('kontonummer');
      this.restService.getKonto(kontonummer).subscribe(konto => this.konto = konto);
    });
  }

  reload() {
    this.ngOnInit();
  }

  setName() {
    this.restService.putKonto(this.konto.kontonummer, { kontonummer: this.konto.kontonummer, betrag: null, name: this.konto.name }).subscribe((data) => {
      this.konto = data;
      this.messageService.add({severity: 'success', summary: 'Kontobezeichnung geändert'});
    }, error => {this.messageService.add({severity: 'error', summary: 'Kontobezeichnung nicht geändert'});});
  }

  betragClass(betrag: string): string {
    return betrag.indexOf('-') > 0 ? 'betrag soll' : 'betrag haben';
  }
}
