import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {MessageService, SelectItem} from 'primeng/api';
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
  zweck: string = '';

  @Output()
  onPayment: EventEmitter<void> = new EventEmitter<void>();

  konten: SelectItem[] = [];

  constructor(private restService: RestService, private messageService: MessageService) { }

  ngOnInit() {
    this.restService.getKonten().subscribe(
      konten => this.konten = konten.map(konto => {

        return {label: konto.kontonummer, value: konto.kontonummer};
      })
    );
  }

  zahlungAnweisen() {
    this.restService.postZahlung(this.quelle,
                                 this.ziel,
                                 'EUR ' + this.euros + '.' + this.cents, this.zweck)
        .subscribe(() => {
                     this.messageService.add({severity: 'success', summary: 'Zahlung erfolgreich ausgeführt'});
                     this.onPayment.emit();
                   },
                   error => {
                     this.messageService.add({
                                               severity: 'error',
                                               summary: 'Fehler',
                                               detail: "Zahlung wird nicht ausgeführt"
                                             });
                   });
  }

}
