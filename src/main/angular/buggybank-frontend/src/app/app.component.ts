import {HttpClient} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {SERVER_API_URL} from './app.constants';

@Component({
  selector: 'spirit-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  title = 'buggybank-frontend';

  constructor() {}

  ngOnInit(): void { }


}
