import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KundenuebersichtComponent } from './kundenuebersicht.component';

describe('KundenuebersichtComponent', () => {
  let component: KundenuebersichtComponent;
  let fixture: ComponentFixture<KundenuebersichtComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ KundenuebersichtComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KundenuebersichtComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
