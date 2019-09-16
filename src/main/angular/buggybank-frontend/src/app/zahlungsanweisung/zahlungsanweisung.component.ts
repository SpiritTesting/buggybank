import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {SelectItem} from 'primeng/api';
import {RestService} from '../rest.service';

@Component({
             selector: 'spirit-zahlungsanweisung',
             templateUrl: './zahlungsanweisung.component.html',
             styleUrls: ['./zahlungsanweisung.component.scss']
           })
export class ZahlungsanweisungComponent implements OnInit {

  @Input()
  quelle: string;
  ziel: string  = '';
  euros: number = 0;
  cents: number = 0;

  @Output()
  onPayment: EventEmitter<void> = new EventEmitter<void>();

  konten: SelectItem[] = [];

  constructor(private restService: RestService) { }

  ngOnInit() {
    this.restService.getKonten().subscribe(konten => this.konten = konten.map(konto => { return {label: konto.kontonummer, value: konto.kontonummer}; }));
  }

  zahlungAnweisen() {
    this.restService.postZahlung(this.quelle, this.ziel, 'EUR ' + this.euros + '.' + this.cents).subscribe(() => {
      this.onPayment.emit();
    });
  }

}
