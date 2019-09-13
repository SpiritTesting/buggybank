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

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.http.get(SERVER_API_URL + 'api/kunde', {observe: 'body'}).subscribe(body => console.log(body));
  }


}
