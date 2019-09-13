import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {KundenuebersichtComponent} from './kundenuebersicht/kundenuebersicht.component';


const routes: Routes = [
  {
    path: 'kunden',
    component: KundenuebersichtComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
