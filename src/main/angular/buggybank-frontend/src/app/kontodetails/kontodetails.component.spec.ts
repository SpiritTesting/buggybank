import {async, ComponentFixture, TestBed, waitForAsync} from '@angular/core/testing';

import { KontodetailsComponent } from './kontodetails.component';
import {DebugElement} from "@angular/core";
import {RestService} from "../rest.service";
import {AppModule} from "../app.module";
import {of} from "rxjs";
import {ACCOUNTS} from "../../server/db-data";
import {ActivatedRoute, provideRoutes} from "@angular/router";

fdescribe('KontodetailsComponent', () => {
  let component: KontodetailsComponent;
  let fixture: ComponentFixture<KontodetailsComponent>;
  let element: DebugElement;
  let restService: any;
  let accountsInArray = [{}];


  beforeEach(waitForAsync(() => {

    accountsInArray = (Object.values(ACCOUNTS))
    TestBed.configureTestingModule({
      declarations: [ KontodetailsComponent ],
      imports: [
        AppModule
      ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: {
                get: () => '12340003',
              }
            }
          }
        }

      ]
    })
    .compileComponents().then(() => {

      fixture = TestBed.createComponent(KontodetailsComponent);
      component = fixture.componentInstance;
      element = fixture.debugElement;
      restService = TestBed.inject(RestService);
    });
  }));


  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get account number from parameters', () => {
    pending();
  });


  fit('should get account from account number', () => {
    spyOn(restService, 'getKonto').and.returnValue(of(accountsInArray[0]))

    component.ngOnInit()
    fixture.detectChanges();

    spyOn(restService.getKonto("12340003"), 'subscribe')
  });
  it('should set name for account success', () => {
    pending();
  });
  it('should set name for account failure', () => {
    pending();
  });
});
