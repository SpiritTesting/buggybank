import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {SERVER_API_URL} from './app.constants';
import {Kunde} from './kundenuebersicht/kunde.model';

@Injectable({
              providedIn: 'root'
            })
export class RestService {

  private kundenUrl = SERVER_API_URL + 'kunde';
  private kontenUrl = SERVER_API_URL + 'konto';

  constructor(private http: HttpClient) {}

  getKunden(): Observable<Kunde[]> {
    return this.http.get<Kunde[]>(this.kundenUrl, {observe: 'body'});
  }

}
