import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {MessageService} from 'primeng/api';
import {RestService} from '../rest.service';
import {Kundendetails} from './kundendetails.model';

@Component({
             selector: 'spirit-kundendetails',
             templateUrl: './kundendetails.component.html',
             styleUrls: ['./kundendetails.component.scss']
           })
export class KundendetailsComponent implements OnInit {

  kunde: Kundendetails;

  constructor(private route: ActivatedRoute, private restService: RestService, private messageService: MessageService) { }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const kundennummer = params.get('kundennummer');
      this.restService.getKunde(kundennummer).subscribe(kunde => this.kunde = kunde);
    });
  }

  neuesKonto() {
    this.restService.postKonto(this.kunde.kundennummer).subscribe(
      () => {
        this.ngOnInit();
        this.messageService.add({severity: 'success', summary: 'Konto angelegt'});
      },
      error => this.messageService.add({severity: 'error', summary: 'Fehler', detail: 'Konto nicht angelegt'}));
  }

  betragClass(betrag: string): string {
    return betrag.indexOf('-') > 0 ? 'betrag soll' : 'betrag haben';
  }
}
