import { ComponentFixture, TestBed, waitForAsync} from '@angular/core/testing';
import { KontodetailsComponent } from './kontodetails.component';
import {DebugElement} from "@angular/core";
import {RestService} from "../rest.service";
import {AppModule} from "../app.module";
import {of} from "rxjs";
import {ACCOUNTS} from "../../server/db-data";
import {ActivatedRoute, convertToParamMap, Router} from "@angular/router";
import {By} from "@angular/platform-browser";

describe('KontodetailsComponent', () => {
  let component: KontodetailsComponent;
  let fixture: ComponentFixture<KontodetailsComponent>;
  let element: DebugElement;
  let restService: any;
  let router: any;
  let accountsInArray = [{}];

  beforeEach(waitForAsync(() => {
    const routerSpy = jasmine.createSpyObj('Router', ['navigate'])
    const restServiceSpy = jasmine.createSpyObj('RestService', ['getKonto', 'getKonten'])
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
              paramMap: of(convertToParamMap({ kontonummer: '12340003'}))
          }
        },
        {
          provide: Router,
          useValue: routerSpy
        },
        {
          provide: RestService,
          useValue: restServiceSpy
        }

      ]
    })
    .compileComponents().then(() => {

      fixture = TestBed.createComponent(KontodetailsComponent);
      component = fixture.componentInstance;
      element = fixture.debugElement;
      restService = TestBed.inject(RestService);
      router = TestBed.inject(Router);
    });
  }));


  it('should create', () => {
    expect(component).toBeTruthy();
  });


  it('should get correct account details', () => {

    restService.getKonto.and.returnValue(of(accountsInArray[0]));
    restService.getKonten.and.returnValue(of(accountsInArray));

    fixture.detectChanges()

      expect(component.konto.kontonummer).toBe("12340003", "Wrong account number");
      expect(component.konto.zahlungen.length).toBe(2, "Unexpected number of accounts");
      expect(component.konto.betrag).toBe('EUR 0.00',"Wrong amount");
      expect(component.konto.name).toBe('First account',"Wrong account name");

  });


  it('should display correct account details', () => {
    restService.getKonto.and.returnValue(of(accountsInArray[0]));
    restService.getKonten.and.returnValue(of(accountsInArray));

    fixture.detectChanges()
    const accountTitle = element.query(By.css('p-panel'));
    expect(accountTitle.nativeElement.textContent).toContain("First account 12340003", "Wrong account title");

    const accountAmount = element.query(By.css('.account-amount'));
    expect(accountAmount.nativeElement.textContent).toBe('EUR 0.00', "Wrong amount")

    const accountLimit = element.query(By.css('.account-limit'));
    expect(accountLimit.nativeElement.textContent).toBe('', "Unexpected account limit")

    const accountActivity = element.queryAll(By.css('.account-activity'));
    expect(accountActivity.length).toBeGreaterThan(0,"Could not find activities");

  });

});
