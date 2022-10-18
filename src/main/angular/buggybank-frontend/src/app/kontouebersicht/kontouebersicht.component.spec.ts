import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KontouebersichtComponent } from './kontouebersicht.component';
import {ACCOUNTS} from "../../server/db-data";
import {setupAccounts} from "../../server/db-data";
import {DebugElement} from "@angular/core";
import {RestService} from "../rest.service";
import {AppModule} from "../app.module";
import {By} from "@angular/platform-browser";
import {of} from "rxjs";


describe('KontouebersichtComponent', () => {
  let component: KontouebersichtComponent;
  let fixture: ComponentFixture<KontouebersichtComponent>;
  let element: DebugElement;
  let restService: any;


  beforeEach(async(() => {
    const restServiceSpy = jasmine.createSpyObj('RestService', ['getKonto','getKonten'])


    TestBed.configureTestingModule({

      declarations: [ KontouebersichtComponent ],
      providers: [
        {provide: RestService, useValue: restServiceSpy}
      ],
      imports: [
        AppModule
      ],
    }).compileComponents().then(() => {

      fixture = TestBed.createComponent(KontouebersichtComponent);
      component = fixture.componentInstance;
      element = fixture.debugElement;
      restService = TestBed.get(RestService);
    });
  }));


  it('should create', () => {

    expect(component).toBeTruthy();
  });
  it('should display list of accounts', () => {

    component.konten = Object.values(ACCOUNTS);
    console.log(component.konten);

    restService.getKonten().and.returnValue(of(setupAccounts()));

    fixture.detectChanges();

  });
  it('should display account details', () => {

    pending();
  });
  it('should convert amount to euro/ cent format', () => {

    pending();
  });
  it('should display sum the amount', () => {

    pending();
  });
});
