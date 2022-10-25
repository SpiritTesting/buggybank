import {ComponentFixture, fakeAsync, TestBed, waitForAsync} from '@angular/core/testing';
import {KontouebersichtComponent } from './kontouebersicht.component';
import {DebugElement} from "@angular/core";
import {RestService} from "../rest.service";
import {AppModule} from "../app.module";
import {of} from "rxjs";
import {ACCOUNTS} from "../../server/db-data";
import {By} from "@angular/platform-browser";

describe('KontouebersichtComponent', () => {

  let fixture: ComponentFixture<KontouebersichtComponent>;
  let component: KontouebersichtComponent;
  let element: DebugElement;
  let restService: any;
  let accountsInArray = [{}];

  beforeEach(waitForAsync(() => {

    TestBed.configureTestingModule({
      imports: [
        AppModule
      ]}).compileComponents()
      .then(() => {

        fixture = TestBed.createComponent(KontouebersichtComponent)
        component = fixture.componentInstance
        element = fixture.debugElement
        restService = TestBed.inject(RestService)
        accountsInArray = (Object.values(ACCOUNTS))
        spyOn(restService, 'getKonten').and.returnValue(of(accountsInArray))
        spyOn(restService, 'getKonto').and.returnValue(of(accountsInArray[3]))
    });
  }));


  it('should create', () => {

    expect(component).toBeTruthy();
  });


  it('should get accounts from service and correct subscribe to value',() => {

    fixture.detectChanges();

    spyOn(restService.getKonto("000"), 'subscribe')
    spyOn(restService.getKonten(), 'subscribe')


    expect(component.sonderkonto).toBeTruthy( "Sonderkonto was not defined")
    expect(component.konten).toBeTruthy("Konten were not defined")

  });

  it('should display accounts list', () => {


    fixture.detectChanges();

    spyOn(restService.getKonten(), 'subscribe')


    const rows = element.queryAll(By.css("tr"));
    expect(rows.length).toBe(5, "Unexpected number of rows found");

  });


  it('should display account details', () => {

    fixture.detectChanges();


    spyOn(restService.getKonten(), 'subscribe')
    spyOn(restService.getKonto("12340003"), 'subscribe')

    fixture.detectChanges();

    const accountNumber = element.query(By.css(".account-number"))
    expect(accountNumber.nativeElement.textContent).toBe('12340003');

    const accountName = element.query(By.css(".account-name"))
    expect(accountName.nativeElement.textContent).toBe('First account');

    const accountAmount = element.query(By.css(".account-amount"))
    expect(accountAmount.nativeElement.textContent).toBe('EUR 0.00');
  });

});
