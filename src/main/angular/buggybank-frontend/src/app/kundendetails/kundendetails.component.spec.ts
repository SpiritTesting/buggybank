import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KundendetailsComponent } from './kundendetails.component';

describe('KundendetailsComponent', () => {
  let component: KundendetailsComponent;
  let fixture: ComponentFixture<KundendetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ KundendetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KundendetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
