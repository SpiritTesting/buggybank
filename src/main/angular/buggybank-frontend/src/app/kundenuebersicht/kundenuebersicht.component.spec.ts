import {ComponentFixture, TestBed, waitForAsync} from '@angular/core/testing';

import { KundenuebersichtComponent } from './kundenuebersicht.component';
import {of} from "rxjs";
import {By} from "@angular/platform-browser";
import {DebugElement} from "@angular/core";
import {AppModule} from "../app.module";
import {RestService} from "../rest.service";
import {USERS} from "../../server/db-data";


describe('KundenuebersichtComponent', () => {
  let component: KundenuebersichtComponent;
  let fixture: ComponentFixture<KundenuebersichtComponent>;
  let element: DebugElement;
  let restService: any;
  let usersInArray = [{}];


  beforeEach(waitForAsync(() => {

    TestBed.configureTestingModule({
      imports: [
        AppModule
      ]}).compileComponents()
      .then(() => {

        fixture = TestBed.createComponent(KundenuebersichtComponent);
        component = fixture.componentInstance;
        element = fixture.debugElement;
        restService = TestBed.inject(RestService);
        usersInArray = (Object.values(USERS))
        spyOn(restService, 'getKunden').and.returnValue(of(usersInArray))
      });
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get users from service and correct subscribe to value',() => {

    fixture.detectChanges();
    spyOn(restService.getKunden(), 'subscribe')

    expect(component.kunden).toBeTruthy("Users were not defined")


  });

  it('should get correct accounts in component', () => {

      fixture.detectChanges();
      spyOn(restService.getKunden(), 'subscribe')


      const user = component.kunden[0]
      expect(user.kundennummer).toBe("KDNR0003", "Wrong user number");
      expect(user.name).toBe("Karo", "Wrong user name");


  });

  it('should display correct accounts in view', () => {

    fixture.detectChanges()
    spyOn(restService.getKunden(), 'subscribe')


    const userNumber = element.query(By.css('.user-number'));
    expect(userNumber.nativeElement.textContent).toContain("KDNR0003", "Wrong user number");

    const userName = element.query(By.css('.user-name'));
    expect(userName.nativeElement.textContent).toBe('Karo', "Wrong user name")


  });

});
