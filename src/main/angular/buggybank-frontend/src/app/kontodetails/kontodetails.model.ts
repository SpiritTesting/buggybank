import {Zahlung} from './zahlung.model';

export class Kontodetails {
  constructor(public kontonummer: string, public betrag: string, public kreditrahmen: string, public zahlungen: Zahlung[]) {}
}
