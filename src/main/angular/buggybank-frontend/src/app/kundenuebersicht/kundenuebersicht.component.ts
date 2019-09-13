import {Component, OnInit} from '@angular/core';
import {RestService} from '../rest.service';
import {Kunde} from './kunde.model';

@Component({
             selector: 'spirit-kundenuebersicht',
             templateUrl: './kundenuebersicht.component.html',
             styleUrls: ['./kundenuebersicht.component.scss']
           })
export class KundenuebersichtComponent implements OnInit {

  kunden: Kunde[] = [];

  constructor(private restService: RestService) { }

  ngOnInit() {
    this.restService.getKunden().subscribe(data => this.kunden = data);
  }

}
