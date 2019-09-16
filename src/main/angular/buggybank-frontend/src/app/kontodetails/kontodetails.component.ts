import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {RestService} from '../rest.service';
import {Kontodetails} from './kontodetails.model';

@Component({
             selector: 'spirit-kontodetails',
             templateUrl: './kontodetails.component.html',
             styleUrls: ['./kontodetails.component.scss']
           })
export class KontodetailsComponent implements OnInit {
  konto: Kontodetails;

  constructor(private route: ActivatedRoute, private restService: RestService) { }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const kontonummer = params.get('kontonummer');
      this.restService.getKonto(kontonummer).subscribe(konto => this.konto = konto);
    });
  }

  reload() {
    this.ngOnInit();
  }
}
