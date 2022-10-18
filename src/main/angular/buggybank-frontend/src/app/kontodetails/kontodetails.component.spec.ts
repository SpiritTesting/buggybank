import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KontodetailsComponent } from './kontodetails.component';
import {DebugElement} from "@angular/core";
import {RestService} from "../rest.service";
import {AppModule} from "../app.module";

describe('KontodetailsComponent', () => {
  let component: KontodetailsComponent;
  let fixture: ComponentFixture<KontodetailsComponent>;
  let element: DebugElement;
  let restService: any;
  beforeEach(async(() => {

    const restServiceSpy = jasmine.createSpyObj('RestService', ['getKonto','putKonto'])

    TestBed.configureTestingModule({
      declarations: [ KontodetailsComponent ],
      providers: [
        {provide: RestService, useValue: restServiceSpy}
      ],
      imports: [
        AppModule
      ],
    })
    .compileComponents().then(() => {

      fixture = TestBed.createComponent(KontodetailsComponent);
      component = fixture.componentInstance;
      element = fixture.debugElement;
      restService = TestBed.get(RestService);
    });
  }));


  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get account number from parameters', () => {
    pending();
  });
  it('should get account from account number ', () => {
    pending();
  });
  it('should set name for account success', () => {
    pending();
  });
  it('should set name for account failure', () => {
    pending();
  });
});
