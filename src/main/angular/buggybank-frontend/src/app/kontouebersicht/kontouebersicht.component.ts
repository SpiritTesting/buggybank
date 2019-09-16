import {Component, OnInit} from '@angular/core';
import {RestService} from '../rest.service';
import {Konto} from './konto.model';

@Component({
             selector: 'spirit-kontouebersicht',
             templateUrl: './kontouebersicht.component.html',
             styleUrls: ['./kontouebersicht.component.scss']
           })
export class KontouebersichtComponent implements OnInit {

  konten: Konto[] = [];

  constructor(private restService: RestService) { }

  ngOnInit() {
    this.restService.getKonten().subscribe(data => this.konten = data);
  }

  sum() {
    return "EUR " + (this.konten.map(konto => this.toCents(konto.betrag)).reduce((a,b) => a+b, 0) / 100);
  }

  private toCents(betrag: string): number {
    const noPrefix = betrag.substr(3).trim();
    const euros = noPrefix.substr(0, noPrefix.indexOf("."));
    const cents = noPrefix.substr(noPrefix.indexOf(".") + 1);
    return Number(euros) * 100 + Number(cents);
  }

}
