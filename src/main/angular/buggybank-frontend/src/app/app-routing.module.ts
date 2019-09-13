import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {KundendetailsComponent} from './kundendetails/kundendetails.component';
import {KundenuebersichtComponent} from './kundenuebersicht/kundenuebersicht.component';


const routes: Routes = [
  {
    path: 'kunden',
    component: KundenuebersichtComponent
  },
  {
    path: 'kunden/:kundennummer',
    component: KundendetailsComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
