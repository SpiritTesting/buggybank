import {Component, OnInit} from '@angular/core';

@Component({
             selector: 'spirit-root',
             templateUrl: './app.component.html',
             styleUrls: ['./app.component.scss']
           })
export class AppComponent implements OnInit {
  title = 'buggybank-frontend';

  constructor() {}

  ngOnInit(): void { }

}
