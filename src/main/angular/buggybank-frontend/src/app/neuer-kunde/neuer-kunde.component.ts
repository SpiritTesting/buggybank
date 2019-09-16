import {Component, OnInit} from '@angular/core';
import {RestService} from '../rest.service';

@Component({
             selector: 'spirit-neuer-kunde',
             templateUrl: './neuer-kunde.component.html',
             styleUrls: ['./neuer-kunde.component.scss']
           })
export class NeuerKundeComponent implements OnInit {

  name: string   = '';
  public display = false;

  constructor(private restService: RestService) { }

  ngOnInit() {
  }

  addKunde() {
    this.restService.postKunde(this.name).subscribe(() => { this.display = false; });
  }

}
