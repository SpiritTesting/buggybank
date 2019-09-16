import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ZahlungsanweisungComponent } from './zahlungsanweisung.component';

describe('ZahlungsanweisungComponent', () => {
  let component: ZahlungsanweisungComponent;
  let fixture: ComponentFixture<ZahlungsanweisungComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ZahlungsanweisungComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ZahlungsanweisungComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
