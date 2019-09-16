import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {KontodetailsComponent} from './kontodetails/kontodetails.component';
import {KontouebersichtComponent} from './kontouebersicht/kontouebersicht.component';
import {KundendetailsComponent} from './kundendetails/kundendetails.component';
import {KundenuebersichtComponent} from './kundenuebersicht/kundenuebersicht.component';
import {NeuerKundeComponent} from './neuer-kunde/neuer-kunde.component';


const routes: Routes = [
  {
    path: 'kunden',
    component: KundenuebersichtComponent
  },
  {
    path: 'kunden/:kundennummer',
    component: KundendetailsComponent
  },
  {
    path: 'konten/:kontonummer',
    component: KontodetailsComponent
  },
  {
    path: 'konten',
    component: KontouebersichtComponent
  },
  {
    path: 'neuer-kunde',
    component: NeuerKundeComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
