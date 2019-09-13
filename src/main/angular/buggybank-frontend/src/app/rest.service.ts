import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {SERVER_API_URL} from './app.constants';
import {Kontodetails} from './kontodetails/kontodetails.model';
import {Kundendetails} from './kundendetails/kundendetails.model';
import {Kunde} from './kundenuebersicht/kunde.model';

@Injectable({
              providedIn: 'root'
            })
export class RestService {

  private kundenUrl = SERVER_API_URL + 'kunde/';
  private kontenUrl = SERVER_API_URL + 'konto/';

  constructor(private http: HttpClient) {}

  getKunden(): Observable<Kunde[]> {
    return this.http.get<Kunde[]>(this.kundenUrl, {observe: 'body'});
  }

  getKunde(kundennummer: string): Observable<Kundendetails> { return this.http.get<Kundendetails>(this.kundenUrl + kundennummer, {observe: 'body'}); }

  getKonto(kontonummer: string): Observable<Kontodetails> { return this.http.get<Kontodetails>(this.kontenUrl + kontonummer, {observe: 'body'}); }
}
