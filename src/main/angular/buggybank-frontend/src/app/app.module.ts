import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import {ButtonModule} from 'primeng/button';
import {DropdownModule} from 'primeng/dropdown';
import {InputTextModule} from 'primeng/inputtext';
import {TableModule} from 'primeng/table';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { KundenuebersichtComponent } from './kundenuebersicht/kundenuebersicht.component';
import { KundendetailsComponent } from './kundendetails/kundendetails.component';
import { KontodetailsComponent } from './kontodetails/kontodetails.component';
import { KontouebersichtComponent } from './kontouebersicht/kontouebersicht.component';
import { ZahlungsanweisungComponent } from './zahlungsanweisung/zahlungsanweisung.component';

@NgModule({
  declarations: [
    AppComponent,
    KundenuebersichtComponent,
    KundendetailsComponent,
    KontodetailsComponent,
    KontouebersichtComponent,
    ZahlungsanweisungComponent
  ],
            imports: [
              BrowserModule,
              BrowserAnimationsModule,
              FormsModule,
              HttpClientModule,
              AppRoutingModule,
              TableModule,
              ButtonModule,
              InputTextModule,
              DropdownModule
            ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
