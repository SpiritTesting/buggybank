import {Kontodetails} from '../kontodetails/kontodetails.model';

export class Kundendetails {

  constructor(public kundennummer: string, public name: string, public konten: Kontodetails[], public saldo: string) {}

}
