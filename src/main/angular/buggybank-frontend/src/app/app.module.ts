import {HttpClientModule} from '@angular/common/http';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ButtonModule} from "primeng/button";
import {DialogModule} from 'primeng/dialog';
import {DropdownModule} from 'primeng/dropdown';
import {InputTextModule} from 'primeng/inputtext';
import {ToolbarModule} from "primeng/toolbar";
import {MessageModule} from "primeng/message";
import {PanelModule} from "primeng/panel";
import {TableModule} from "primeng/table";
import {ToastModule} from 'primeng/toast';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {KontodetailsComponent} from './kontodetails/kontodetails.component';
import {KontouebersichtComponent} from './kontouebersicht/kontouebersicht.component';
import {KundendetailsComponent} from './kundendetails/kundendetails.component';
import {KundenuebersichtComponent} from './kundenuebersicht/kundenuebersicht.component';
import {NeuerKundeComponent} from './neuer-kunde/neuer-kunde.component';
import {ZahlungsanweisungComponent} from './zahlungsanweisung/zahlungsanweisung.component';
import {MessageService} from "primeng/api";

@NgModule({
            declarations: [
              AppComponent,
              KundenuebersichtComponent,
              KundendetailsComponent,
              KontodetailsComponent,
              KontouebersichtComponent,
              ZahlungsanweisungComponent,
              NeuerKundeComponent
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
              DropdownModule,
              DialogModule,
              ToolbarModule,
              PanelModule,
              MessageModule,
              ToastModule
            ],
            providers: [MessageService],
            bootstrap: [AppComponent]
          })
export class AppModule {}
