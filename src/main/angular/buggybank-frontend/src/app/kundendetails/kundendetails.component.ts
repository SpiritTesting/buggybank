import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Kunde} from '../kundenuebersicht/kunde.model';
import {RestService} from '../rest.service';
import {Kundendetails} from './kundendetails.model';

@Component({
  selector: 'spirit-kundendetails',
  templateUrl: './kundendetails.component.html',
  styleUrls: ['./kundendetails.component.scss']
})
export class KundendetailsComponent implements OnInit {

  kunde: Kundendetails;

  constructor(private route: ActivatedRoute, private restService: RestService) { }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const kundennummer = params.get('kundennummer');
      this.restService.getKunde(kundennummer).subscribe(kunde => this.kunde = kunde);
    });
  }

}
